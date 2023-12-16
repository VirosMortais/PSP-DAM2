package org.virosms;

import org.eclipse.paho.client.mqttv3.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * La clase CarSimulator implementa Runnable y simula un coche que genera y publica datos de velocidad en un broker MQTT.
 * Cada segundo, genera una velocidad aleatoria entre 60 y 140 y una matrícula aleatoria, y las publica en un topic MQTT.
 * Cuando recibe una multa de "PoliceStation.java", la muestra en la pantalla.
 *
 * @author VirosMortais
 */
public class CarSimulator implements Runnable {

    /**
     * Cliente MQTT para la comunicación con el broker MQTT.
     */
    private final MqttClient client;

    /**
     * Generador de números aleatorios para la generación de velocidades y matrículas.
     */
    private final Random rand = new Random();


    /**
     * Constructor de la clase CarSimulator.
     *
     * @param url La URL del broker MQTT.
     * @throws MqttException Si ocurre un error al conectar con el broker MQTT.
     */
    public CarSimulator(String url) throws MqttException {
        this.client = new MqttClient(url, UUID.randomUUID().toString());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        client.connect();
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {}
            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) {
                String[] fine = mqttMessage.toString().split(":");
                System.out.printf("(%s): %s - %.2f€%n",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                        fine[1], Double.parseDouble(fine[2]));
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        client.subscribe("car/ticket");
    }

    /**
     * Método principal de la clase que se ejecuta en un hilo separado.
     * Cada segundo, genera una velocidad aleatoria entre 60 y 140 y una matrícula aleatoria, y las publica en un topic MQTT.
     */
    @Override
    public void run() {
        do {
            try {
                int speed = rand.nextInt(60, 141);
                StringBuilder licensePlate = new StringBuilder();
                licensePlate.append(String.format("%04d", rand.nextInt(10000)));
                for (int i = 0; i < 3; i++) {
                    licensePlate.append((char)rand.nextInt('A', 'Z'+1));
                }

                if(client.isConnected()) {
                    String topic = "car/data";
                    byte[] payload = (licensePlate + ":" + speed).getBytes();
                    MqttMessage msg = new MqttMessage(payload);
                    msg.setQos(0);
                    msg.setRetained(true);
                    client.publish(topic, msg);
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }while (true);
    }
}