package org.example;

/**
 * Constastes es una clase que contiene constantes para la aplicación.
 */
public class Constastes {
    /**
     * Opciones de menú para el usuario.
     */
    public static final String MENU = "=".repeat(20) + "Menu" + "=".repeat(20);

    public static final String REPEAT = "=".repeat(40);


    public static final String PLEASE_ENTER_A_COMMAND = "Please enter a command: ";


    /**
     * Opción para salir de la aplicación.
     */
    public static final String EXIT = "EXIT";
    /**
     * Opción para obtener las últimas mediciones de una estación meteorológica específica.
     */
    public static final String LAST = "LAST ID. Displays the latest measurements from the weather station with that ID.";
    /**
     * Opción para obtener la temperatura más alta de una estación meteorológica específica.
     */
    public static final String MAXTEMP = "MAXTEMP ID. Displays the highest temperature from the weather station with that ID.";
    /**
     * Opción para obtener la temperatura más alta de todas las estaciones meteorológicas.
     */
    public static final String MAXTEMP_ALL = "MAXTEMP_ALL. Displays the highest temperature in the system (searches all weather stations).";
    /**
     * Opción para obtener las alertas actuales y eliminarlas.
     */
    public static final String ALERTS = "ALERTS. Displays the current alerts and deletes them.";

    /**
     * Mensaje de error cuando se pierde la conexión con el broker Solace.
     */
    public static final String CONNECTION_LOST = "Connection to Solace broker lost!";
    /**
     * Mensaje de alerta cuando se detecta una temperatura extrema.
     */
    public static final String EXTREME_TEMPERATURE_ALERT = "Alert for extreme temperatures on %s at %s at station %s";

    /**
     * Dirección del servidor MQTT.
     */
    public static final String MQTT_SERVER = "tcp://184.73.34.167:1883";


    /**
     * The URL of the Redis server.
     */
    public static final String REDIS_SERVER_URL = "184.73.34.167";

    /**
     * The port of the Redis server.
     */
    public static final int REDIS_SERVER_PORT = 6379;

    /**
     * Formato del topic para las estaciones meteorológicas.
     */
    public static final String TOPIC_FORMAT = "/CHA/METEO/%s/MEASUREMENTS/";
    /**
     * Formato del mensaje que se envía a las estaciones meteorológicas.
     */
    public static final String MESSAGE_FORMAT = "date=%s#hour=%s#temperature%s";
    /**
     * Formato de la fecha que se utiliza en los mensajes.
     */
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    /**
     * Formato de la hora que se utiliza en los mensajes.
     */
    public static final String TIME_FORMAT = "HH:mm";

    public static final String STOP_STRING = "STOP ID. Stops the weather station with that ID.";

}