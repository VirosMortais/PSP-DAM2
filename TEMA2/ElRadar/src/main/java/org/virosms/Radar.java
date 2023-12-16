package org.virosms;

import org.eclipse.paho.client.mqttv3.*;
import redis.clients.jedis.Jedis;
import java.util.UUID;

/**
 * La clase Radar implementa Runnable y simula un radar móvil que detecta la velocidad de los vehículos.
 * Si un vehículo supera la velocidad límite, se crea una entrada en Redis.
 * Si un vehículo no supera la velocidad límite, se añade a un grupo en Redis para llevar un registro de los vehículos que han pasado por el radar.
 *
 * @author VirosMortais
 */
public class Radar implements Runnable {

    /**
     * Cliente MQTT para la comunicación con el broker MQTT.
     */
    private MqttClient client;

    /**
     * Velocidad del vehículo detectada por el radar.
     */
    private int speed;

    /**
     * Matrícula del vehículo detectada por el radar.
     */
    private String licensePlate;

    /**
     * URL del servidor Redis.
     */
    private final String redisUrl;

    /**
     * Nombre del grupo en Redis que almacena los vehículos que han pasado por el radar.
     */
    public static final String VEHICLES = "CHARLES:VEHICLES";


    /**
     * Constructor de la clase Radar.
     *
     * @param mqttUrl La URL del broker MQTT.
     * @param redisUrl La URL del servidor Redis.
     * @throws MqttException Si ocurre un error al conectar con el broker MQTT.
     */
    public Radar(String mqttUrl, String redisUrl) throws MqttException {
        this.redisUrl = redisUrl;
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
            public void messageArrived(String topic, MqttMessage mqttMessage) {
                String[] data = mqttMessage.toString().split(":");
                licensePlate = data[0];
                speed = Integer.parseInt(data[1]);
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        client.subscribe("car/data");
    }

    /**
     * Método principal de la clase que se ejecuta en un hilo separado.
     * Cada segundo, si se ha detectado una matrícula, comprueba si la velocidad asociada supera el límite.
     * Si la velocidad supera el límite, publica un mensaje en el topic "car/excess" y añade la matrícula a Redis.
     * Si la velocidad no supera el límite, añade la matrícula al grupo "VEHICULOS" en Redis.
     */
    @Override
    public void run() {
        try(Jedis jedis = new Jedis(redisUrl, 6379)){
            do {
                try {
                    if(licensePlate != null){
                        if (speed > 80){
                            String msg = String.format("EXCESS:%d:%s", speed, licensePlate);
                            MqttMessage message = new MqttMessage(msg.getBytes());
                            client.publish("car/excess", message);
                        }
                        jedis.rpush(VEHICLES, licensePlate);
                        licensePlate = null;
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }while (true);
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}