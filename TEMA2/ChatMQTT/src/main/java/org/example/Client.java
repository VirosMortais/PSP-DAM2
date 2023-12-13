package org.example;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * La clase Client se encarga de la comunicación MQTT.
 * Se conecta a un broker MQTT, se suscribe a los topics relevantes y proporciona métodos para enviar y recibir mensajes.
 */
public class Client {
    private static final String broker = "tcp://broker.hivemq.com:1883";
    private static final String clientId = "JavaSample";
    private MqttClient client;
    private Map<String, PrintWriter> chats = new HashMap<>();
    private String username;
    private String url;
    private String path;

    /**
     * Constructor de la clase Client.
     * Inicializa el cliente MQTT.
     *
     * @param username El nombre de usuario que se utilizará para la conexión MQTT.
     * @param url La URL del broker MQTT al que se conectará el cliente.
     * @param path La ruta donde se guardarán los archivos de chat.
     * @throws MqttException Si ocurre un error al inicializar el cliente MQTT.
     * @throws IOException Si ocurre un error al abrir el archivo de chat.
     */
    public Client(String username, String url, String path) throws MqttException, IOException {
        this.username = username;
        this.url = url;
        this.path = path;
        client = new MqttClient(broker, clientId, new MemoryPersistence());
    }

    /**
     * Conecta el cliente MQTT al broker y establece las opciones de conexión.
     * Las opciones de conexión incluyen una sesión limpia y la inicialización de los callbacks.
     * @throws RuntimeException Sí ocurre un error al conectar el cliente MQTT o al inicializar los callbacks.
     */
    public void connect() {
        try {
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect(connOpts);
            initCallbacks();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Se suscribe a un topic MQTT y abre un archivo de chat para ese topic.
     *
     * @param topic El topic al que suscribirse.
     * @throws MqttException Si ocurre un error al suscribirse al topic.
     * @throws IOException Si ocurre un error al abrir el archivo de chat.
     */
    public void subscribe(String topic) throws MqttException, IOException {
        client.subscribe(topic, 2);
        chats.put(topic, new PrintWriter(new FileWriter(topic.replace("/", "_") + ".txt", true)));
    }

    /**
     * Envía un mensaje a un topic MQTT y escribe el mensaje en el archivo de chat correspondiente.
     *
     * @param topic El topic al que enviar el mensaje.
     * @param content El contenido del mensaje.
     * @throws MqttException Si ocurre un error al enviar el mensaje.
     */
    public void sendMessage(String topic, String content) throws MqttException {
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(2);
        client.publish(topic, message);
        chats.get(topic).println("Sent: " + content);
        chats.get(topic).flush();
    }

    /**
     * Establece el callback para el cliente MQTT.
     *
     * @param callback El callback a establecer.
     */
    public void setCallback(MqttCallback callback) {
        client.setCallback(callback);
    }

    /**
     * Muestra el contenido del archivo de chat para un topic específico.
     *
     * @param topic El topic del chat a mostrar.
     * @throws IOException Si ocurre un error al leer el archivo de chat.
     */
    public void displayChat(String topic) throws IOException {
        Path filePath = Path.of(".",  "_chat_"+ topic.replace("/", "_") + ".txt");
        File file = filePath.toFile();

        if(file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        }else{
            System.out.println("No chat found");
        }
    }

    /**
     * Desconecta y cierra el cliente MQTT.
     */
    public void close() {
        try {
            if (client.isConnected()) {
                client.disconnect();
            }
            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene todos los mensajes de un archivo de chat para un usuario específico.
     *
     * @param username El nombre de usuario del chat a obtener.
     * @return Una cadena con todos los mensajes del chat.
     * @throws IOException Si ocurre un error al leer el archivo de chat.
     */
    public String getMessages(String username) throws IOException {
        Path filename = Path.of(path, "_chat_" + username + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(filename.toFile()));
        return br.lines().collect(Collectors.joining("\r\n"));
    }

    // Inicializa los callbacks para el cliente MQTT, incluyendo la suscripción a los topics relevantes.
    private void initCallbacks() throws MqttException {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {}

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                writeReceived(topic, mqttMessage);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                try {
                    String topic = iMqttDeliveryToken.getTopics()[0];
                    MqttMessage mqttMessage = iMqttDeliveryToken.getMessage();
                    writeSent(topic, mqttMessage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        String[] topics = new String[]{"/%s/todos".formatted(url), "/%s/+/%s".formatted(url, username)};
        client.subscribe(topics, new int[]{0,0});
    }

    // Genera el topic para enviar un mensaje, dependiendo de si el mensaje es para todos o para un usuario específico.
    private String getChatTopicSend(String otherUser){
        String topic;
        if (otherUser.equals("todos")){
            topic = String.format("/%s/%s", url, otherUser);
        } else {
            topic = String.format("/%s/%s/%s", url, username, otherUser);
        }
        return topic;
    }

    // Escribe un mensaje recibido en el archivo de chat correspondiente.
    private void writeReceived(String topic, MqttMessage mqttMessage) throws IOException {
        String[] topicSplit = topic.split("/");
        writeMessage(Path.of(path, topicSplit[1] + topicSplit[2]), mqttMessage);
    }

    // Escribe un mensaje enviado en el archivo de chat correspondiente.
    private void writeSent(String topic, MqttMessage mqttMessage) throws IOException {
        String[] topicSplit = topic.split("/");
        if (!topicSplit[2].equals("todos")) {
            writeMessage(Path.of(path, topicSplit[1] + topicSplit[3]), mqttMessage);
        }
    }

    // Escribe un mensaje en el archivo de chat correspondiente.
    private void writeMessage(Path filename, MqttMessage mqttMessage) throws IOException {
        Path filePath = Path.of(".", filename.toString());
        File file = filePath.toFile();
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        bw.write("%s (%s): %s".formatted(username, LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM HH:mm")), mqttMessage.toString()));
        bw.newLine();
        bw.close();
    }
}