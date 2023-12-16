package org.virosms;

import org.eclipse.paho.client.mqttv3.*;
import redis.clients.jedis.Jedis;

import java.util.UUID;


/**
 * La clase PoliceStation implementa Runnable y simula una estación de policía que procesa las multas de velocidad.
 * Cuando se detecta una velocidad excesiva, se envía una multa por MQTT y se añade la matrícula del vehículo a un grupo en Redis.
 * Además, muestra estadísticas de los vehículos y las multas cada segundo.
 *
 * @author VirosMortais
 */
public class PoliceStation implements Runnable {

    /**
     * Cliente MQTT para la comunicación con el broker MQTT.
     */
    private final MqttClient client;

    /**
     * Cliente Jedis para la comunicación con el servidor Redis.
     */
    private final Jedis jedis;

    /**
     * Nombre del grupo en Redis que almacena los vehículos que han pasado por el radar.
     */
    public static final String VEHICLES = "CHARLES:VEHICLES";

    /**
     * Nombre del grupo en Redis que almacena los vehículos que han sido multados.
     */
    public static final String FINEDVEHICLES = "CHARLES:FINEDVEHICLES";


    /**
     * Constructor de la clase PoliceStation.
     *
     * @param mqttUrl La URL del broker MQTT.
     * @param redisUrl La URL del servidor Redis.
     * @throws MqttException Si ocurre un error al conectar con el broker MQTT.
     */
    public PoliceStation(String mqttUrl, String redisUrl) throws MqttException {
        this.client = new MqttClient(mqttUrl, UUID.randomUUID().toString());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        client.connect();
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {}
            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                String[] data = mqttMessage.toString().split(":");
                if(data[0].equals("EXCESS") && data[2]!=null) {
                    int percentage = (10000/Integer.parseInt(data[1]))-100;
                    int fine;
                    if (percentage >= 10 && percentage < 20) {
                        fine = 100;
                    }else if (percentage >= 20 && percentage < 30) {
                        fine = 200;
                    }else {
                        fine = 300;
                    }
                    String msg = String.format("TICKET:%s:%d", data[2], fine);
                    MqttMessage message = new MqttMessage(msg.getBytes());
                    client.publish("car/ticket", message);
                    jedis.rpush(FINEDVEHICLES, data[2]);
                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        client.subscribe("car/excess");
        this.jedis = new Jedis(redisUrl, 6379);
        jedis.del(FINEDVEHICLES, VEHICLES);
    }


    /**
     * Método principal de la clase que se ejecuta en un hilo separado.
     * Cada segundo, procesa las multas de velocidad y muestra estadísticas de los vehículos y las multas.
     */
    @Override
    public void run() {
        do {
            try {
                long vehiclesLength = jedis.llen(VEHICLES);
                long finedVehiclesLength = jedis.llen(FINEDVEHICLES);
                double percentage = (double)finedVehiclesLength/vehiclesLength*100;
                System.out.printf("Total vehicles: %d\n", vehiclesLength);
                System.out.printf("Total tickets: %.2f%%(%d fined vehicles)\n", finedVehiclesLength == 0 ? 0 : percentage, finedVehiclesLength);
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }while (true);
    }
}