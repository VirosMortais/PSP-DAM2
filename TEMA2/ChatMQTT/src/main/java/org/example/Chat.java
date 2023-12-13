package org.example;

import org.eclipse.paho.client.mqttv3.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * La clase Chat es la clase principal de la aplicación.
 * Se encarga de la interacción con el usuario y de coordinar las acciones del cliente MQTT.
 */
public class Chat {
    public static void main(String[] args) {
        try {
            Client client = null;
            try {
                client = new Client("juan", "broker.hivemq.com:1883", "");
                client.connect();
            } catch (Exception e) {
                System.out.println("Error al conectar el cliente MQTT.");
            }

            // Lista de estudiantes.
            List<String> students = Arrays.asList("todos", "david", "juan");

            // Suscribe al cliente a los topics de los estudiantes.
            for (String student : students) {
                assert client != null;
                client.subscribe("/chat/" + student);
            }

            // Establece el callback para el cliente MQTT.
            Client finalClient = client;
            client.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause) {}

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Message arrived. Topic: " + topic + "  Message: " + message.toString());
                }

                public void deliveryComplete(IMqttDeliveryToken token) {}
            });

            // Crea un BufferedReader para leer la entrada del usuario.
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            String command = "";

            // Bucle principal que solicita comandos al usuario y realiza las acciones correspondientes.
            do {
                try {
                    System.out.print("\nEnter command (help to command guide): ");
                    String input = sc.readLine();
                    String[] fullCommand = input.split(" ", 3);
                    command = fullCommand[0];

                    switch (command) {
                        case "send":
                            client.sendMessage("/chat/" + fullCommand[1], fullCommand[2]);
                            System.out.printf("\nSending %s to %s\r\n", fullCommand[2], fullCommand[1]);
                            break;
                        case "chat":
                            client.displayChat(fullCommand[1]);
                            System.out.println(client.getMessages(fullCommand[1]));
                            break;
                        case "help":
                            System.out.println("""
                                    send <topic> <message> - send a message to a topic
                                    chat <topic> - get all messages from a topic
                                    exit - exit the program
                                    help - show this help message
                                    """);
                            break;
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    sc = new BufferedReader(new InputStreamReader(System.in));//reset scanner
                }
            } while (!command.equals("exit"));

            // Cierra el cliente MQTT.
            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}