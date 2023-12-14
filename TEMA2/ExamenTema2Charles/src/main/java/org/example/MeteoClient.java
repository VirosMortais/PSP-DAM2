package org.example;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static org.example.Constastes.*;

/**
 * MeteoClient es una clase que interactúa con una estación meteorológica.
 * Proporciona funcionalidades para obtener las últimas mediciones, la temperatura máxima y las alertas actuales.
 */
public class MeteoClient {



    /**
     * El método principal que ejecuta la aplicación.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        String option, command, id = "";
        Scanner sc = new Scanner(System.in);
        try (Jedis jedis = new Jedis(REDIS_SERVER_URL, REDIS_SERVER_PORT)) {
            do {

                printMenu();
                option = sc.nextLine();
                System.out.println("Option: " + option);
                String[] parts = option.split(" ");
                command = parts[0];
                if (parts.length > 1) {
                    id = parts[1];
                } else {
                    id = "";
                }

                if (!command.equalsIgnoreCase("alerts")
                        && !command.equalsIgnoreCase("exit")
                        && !command.equalsIgnoreCase("maxtemp_all")
                        && parts.length == 1) {
                    System.out.println("Por favor, proporciona un ID para este comando.");
                    continue;
                }

                switch (command.toUpperCase()) {
                    case "LAST" -> printLastMeasurements(jedis, id);
                    case "MAXTEMP" -> printMaxTemperature(jedis, id);
                    case "MAXTEMP_ALL" -> printMaxTemperatureAll(jedis);
                    case "ALERTS" -> printAndDeleteAlerts(jedis);
                    case "STOP" -> stopStation(jedis, id);
                    case "EXIT" -> System.out.println("Bye!");
                }
            } while (!option.equalsIgnoreCase("exit"));
        }
    }

    private static void stopStation(Jedis jedis, String id) {
        jedis.publish("STOP", id);
    }

    /**
     * Imprime el menú en la consola.
     */
    private static void printMenu() {
        System.out.println(MENU);
        System.out.println(EXIT);
        System.out.println(LAST);
        System.out.println(MAXTEMP);
        System.out.println(MAXTEMP_ALL);
        System.out.println(ALERTS);
        System.out.println(STOP_STRING);
        System.out.println(REPEAT);
        System.out.print(PLEASE_ENTER_A_COMMAND);
    }

    /**
     * Imprime las últimas mediciones de una estación meteorológica específica.
     *
     * @param jedis la instancia de Jedis para interactuar con la base de datos Redis
     * @param id    el ID de la estación meteorológica
     */
    private static void printLastMeasurements(Jedis jedis, String id) {
        System.out.println("Last measurements for station " + id + ":");
        String datetime = jedis.hget(String.format("CHA:LASTMEASUREMENT:%s", id), "datetime");
        if (datetime != null) {
            System.out.println("datetime: " + datetime);
        } else {
            System.out.println("No previous time.");
        }
    }

    /**
     * Imprime la temperatura máxima de una estación meteorológica específica o de todas las estaciones.
     *
     * @param jedis la instancia de Jedis para interactuar con la base de datos Redis
     * @param id    el ID de la estación meteorológica o "all" para todas las estaciones
     */
    private static void printMaxTemperature(Jedis jedis, String id) {
        if (!id.equalsIgnoreCase("all")) {
            String temperature = jedis.hget(String.format("CHA:LASTMEASUREMENT:%s", id), "temperature");
            System.out.println(temperature);
            if (temperature != null) {
                System.out.println("temperature: " + temperature);
            } else {
                System.out.println("No previous time.");
            }
        }
    }

    /**
     * Este método se utiliza para imprimir la temperatura máxima en todas las estaciones meteorológicas.
     * Recupera todas las claves que coinciden con el patrón "ABC:TEMPERATURES:*" de la base de datos de Redis.
     * Para cada clave, recupera la lista de temperaturas y las recorre en iteración.
     * Si una temperatura es superior a la temperatura máxima actual, actualiza la temperatura máxima y el ID de la estación.
     * Finalmente imprime la temperatura más alta del sistema y el ID de la estación donde fue registrada.
     *
     * @param jedis la instancia de Jedis para interactuar con la base de datos Redis
     */
    private static void printMaxTemperatureAll(Jedis jedis) {

        // Coge todas las claves que coinciden con el patrón "ABC:TEMPERATURES:*" de la base de datos de Redis.
        Set<String> allKeys = jedis.keys("CHA:TEMPERATURES:*");
        String maxTemp = "-Infinity";
        String maxTempStation = "";

        // Para cada clave, recupera la lista de temperaturas y las recorre en iteración.
        for (String key : allKeys) {

            // Si una temperatura es superior a la temperatura máxima actual, actualiza la temperatura máxima y el ID de la estación.
            List<String> temperatures = jedis.lrange(key, 0, -1);


            // Si una temperatura es superior a la temperatura máxima actual, actualiza la temperatura máxima y el ID de la estación.
            for (String temp : temperatures) {
                String[] tempParts = temp.split("=");
                if (tempParts.length > 1) {
                    temp = tempParts[1].replace(',', '.'); // Replace commas with dots
                    if (Float.parseFloat(temp) > Float.parseFloat(maxTemp)) {
                        maxTemp = temp;
                        maxTempStation = key.split(":")[2];
                    }
                }
            }
        }

        // Finalmente imprime la temperatura más alta del sistema y el ID de la estación donde fue registrada.
        System.out.println("The highest temperature in the system is " + maxTemp + " at station " + maxTempStation);
    }

    /**
     * Imprime las alertas actuales y las elimina.
     *
     * @param jedis la instancia de Jedis para interactuar con la base de datos Redis
     */
    private static void printAndDeleteAlerts(Jedis jedis) {
        List<String> alerts = jedis.lrange("CHA:ALERTS", 0, -1);
        if (alerts != null) {
            alerts.forEach(System.out::println);
            jedis.del("CHA:ALERTS");
        } else {
            System.out.println("No alerts.");
        }
    }
}