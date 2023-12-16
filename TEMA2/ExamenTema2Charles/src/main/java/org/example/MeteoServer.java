package org.example;

import org.eclipse.paho.client.mqttv3.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.example.Constastes.*;

/**
 * MeteoServer es una clase que interactúa con varias estaciones meteorológicas.
 * Proporciona funcionalidades para procesar y almacenar las mediciones de temperatura.
 */
public class MeteoServer {

    /**
     * El método principal que ejecuta la aplicación.
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) throws MqttException {
        // Crear un pool de hilos para las estaciones meteorológicas
        int maxThreads = 10;
        ExecutorService threadPool = Executors.newFixedThreadPool(maxThreads);
        for (int i = 0; i < maxThreads; i++) {
            threadPool.execute(new MeteoStation(i));
        }

        // Crear un ID único para el publicador
        String publisherId = UUID.randomUUID().toString();


        try (MqttClient mqttClient = new MqttClient(MQTT_SERVER, publisherId)) {
            // Configurar las opciones de conexión para el publicador
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setConnectionTimeout(10);
            mqttClient.connect(mqttConnectOptions);

            // Establecer el callback para el publicador
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    // Imprimir el mensaje de error cuando se pierde la conexión
                    System.out.println(CONNECTION_LOST + throwable.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) {
                    // Procesar el mensaje cuando llega
                    processMessage(topic, mqttMessage);
                }


                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    // No se necesita hacer nada cuando la entrega está completa
                }
            });

            // Suscribirse al topic de las estaciones meteorológicas
            mqttClient.subscribe("/CHA/METEO/#", 0);


            try (Jedis jedis = new Jedis(REDIS_SERVER_URL, REDIS_SERVER_PORT)) {
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        if (channel.equals("STOP")) {
                            try {
                                mqttClient.publish(String.format("/CHA/METEO/%s/STOP", message), new MqttMessage("STOP".getBytes()));
                            } catch (MqttException e) {
                                System.err.println(e.getMessage());
                            }
                        }
                    }
                }, "STOP");
            }
        } catch (MqttException e) {
            // Lanzar una excepción en caso de error
            throw new RuntimeException(e);
        }
    }

    /**
     * Procesa el mensaje recibido de una estación meteorológica.
     * Almacena la última medición, la lista de temperaturas y genera alertas si es necesario.
     * @param topic el topic del mensaje MQTT
     * @param mqttMessage el mensaje MQTT recibido
     */

    private static void processMessage(String topic, MqttMessage mqttMessage) {
        // Extract the weather station ID from the topic
        String extractWeatherStationId = extractWeatherStationId(topic);

        // Create the keys for the hash and the list in Redis
        String lastMeasurementKey = createLastMeasurementKey(extractWeatherStationId);
        String temperaturesKey = createTemperaturesKey(extractWeatherStationId);
        String alertsKey = "CHA:ALERTS";

        // Split the message into date, time, and temperature
        String[] messageParts = splitMessage(mqttMessage);
        String dateTime = combineDateAndTime(messageParts);
        String temperature = messageParts[2];

        // Process the message in Redis
        processMessageInRedis(lastMeasurementKey, dateTime, temperature, temperaturesKey, alertsKey, messageParts, extractWeatherStationId);
    }

    /**
     * Extrae el ID de la estación meteorológica del topic.
     * @param topic el topic del mensaje MQTT
     * @return el ID de la estación meteorológica
     */
    private static String extractWeatherStationId(String topic) {
        return topic.split("/")[3];
    }

    /**
     * Crea la clave para el hash de la última medición en Redis.
     * @param weatherStationId el ID de la estación meteorológica
     * @return la clave para el hash de la última medición
     */
    private static String createLastMeasurementKey(String weatherStationId) {
        return String.format("CHA:LASTMEASUREMENT:%s", weatherStationId);
    }

    /**
     * Crea la clave para la lista de temperaturas en Redis.
     * @param weatherStationId el ID de la estación meteorológica
     * @return la clave para la lista de temperaturas
     */
    private static String createTemperaturesKey(String weatherStationId) {
        return String.format("CHA:TEMPERATURES:%s", weatherStationId);
    }

    /**
     * Divide el mensaje en fecha, hora y temperatura.
     * @param mqttMessage el mensaje MQTT recibido
     * @return un array con la fecha, la hora y la temperatura
     */
    private static String[] splitMessage(MqttMessage mqttMessage) {
        return new String(mqttMessage.getPayload()).split("#");
    }

    /**
     * Combina la fecha y la hora en una sola cadena.
     * @param messageParts un array con la fecha, la hora y la temperatura
     * @return una cadena con la fecha y la hora
     */
    private static String combineDateAndTime(String[] messageParts) {
        return messageParts[0] + " " + messageParts[1];
    }

    /**
     * Procesa el mensaje en Redis.
     * Elimina el hash anterior y guarda la última medición.
     * Añade la temperatura a la lista de temperaturas.
     * Si la temperatura es extrema, añade una alerta a la lista de alertas.
     * @param lastMeasurementKey la clave para el hash de la última medición
     * @param dateTime la fecha y la hora de la medición
     * @param temperature la temperatura de la medición
     * @param temperaturesKey la clave para la lista de temperaturas
     * @param alertsKey la clave para la lista de alertas
     * @param messageParts un array con la fecha, la hora y la temperaturaS
     * @param weatherStationId el ID de la estación meteorológica
     */
    private static void processMessageInRedis(String lastMeasurementKey, String dateTime, String temperature, String temperaturesKey, String alertsKey, String[] messageParts, String weatherStationId) {
        try (Jedis jedis = new Jedis(REDIS_SERVER_URL, REDIS_SERVER_PORT)) {
            // Delete the previous hash and save the last measurement
            jedis.del(lastMeasurementKey);
            jedis.hset(lastMeasurementKey, "datetime", dateTime);
            jedis.hset(lastMeasurementKey, "temperature", temperature);


            // Add the temperature to the list of temperatures
            jedis.rpush(temperaturesKey, temperature);

            // If the temperature is extreme, add an alert to the list of alerts
            if (Integer.parseInt(temperature) > 30 || Integer.parseInt(temperature) < 0) {
                jedis.rpush(alertsKey, String.format(EXTREME_TEMPERATURE_ALERT, messageParts[0], messageParts[1], weatherStationId));
            }
        }
    }
}