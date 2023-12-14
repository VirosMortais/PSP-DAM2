package org.example;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import static org.example.Constastes.*;

/**
 * MeteoStation es una clase que simula una estación meteorológica.
 * Implementa Runnable, lo que le permite ejecutarse como un hilo separado.
 * Cada MeteoStation tiene una identificación única y envía continuamente datos meteorológicos a un tema MQTT específico.
 */
public class MeteoStation implements Runnable {


    private final int id;
    private final MqttClient mqttClient;
    private volatile boolean running = true; // Flag para controlar el estado de ejecución del hilo

    /**
     * Constructor para la clase MeteoStation.
     * Inicializa el ID y el cliente MQTT.
     *
     * @param id el ID único de la estación meteorológica
     * @throws MqttException si ocurre un error al configurar el cliente MQTT
     */
    public MeteoStation(int id) throws MqttException {
        this.id = id;
        String publisherId = UUID.randomUUID().toString();
        mqttClient = new MqttClient(MQTT_SERVER, publisherId);
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setConnectionTimeout(10);
        mqttClient.connect(mqttConnectOptions);
    }


    /**
     * El método de run, que se llama cuando se inicia el hilo.
     * Envía continuamente datos meteorológicos al tema MQTT hasta que se detiene el hilo.
     */
    @Override
    public void run() {
        Random random = new Random();
        String date, hour, temperature, message;
        String topic = String.format(TOPIC_FORMAT, this.id);


        // Suscribe al topic de STOP
        try {
            mqttClient.subscribe(String.format("/CHA/METEO/%s/STOP", this.id), (topic1, message1) -> {
                if (new String(message1.getPayload()).equals("STOP")) {
                    running = false; // Detener el hilo cuando se recibe el mensaje STOP
                }
            });
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

        // Enviar datos meteorológicos al topic 
        while (running) {
            date = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
            hour = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT));
            temperature = String.format("%.2f", (random.nextDouble() * 50) - 10);
            message = String.format(MESSAGE_FORMAT, date, hour, temperature);
            try {
                mqttClient.publish(topic, new MqttMessage(message.getBytes()));
                System.out.println("Topic: " + topic+ "Message sent: " + message);
                Thread.sleep(5000);
            } catch (MqttException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}