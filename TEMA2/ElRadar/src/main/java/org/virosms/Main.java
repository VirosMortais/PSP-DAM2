package org.virosms;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * La clase Main contiene el método principal que inicia la simulación.
 * Crea instancias de CarSimulator, Radar y PoliceStation y las ejecuta en hilos separados.
 *
 * @author VirosMortais
 */
public class Main {

    /**
     * Método principal que inicia la simulación.
     * Crea instancias de CarSimulator, Radar y PoliceStation y las ejecuta en hilos separados.
     *
     * @param args Argumentos de la línea de comandos. No se utilizan en este programa.
     */
    public static void main(String[] args) {
        // Declaración de constantes
        String url = "34.228.162.124";
        String mqttUrl = String.format("tcp://%s:1883", url);

        // Declaración de objetos Runnable
        CarSimulator simulator;
        try {
            simulator = new CarSimulator(mqttUrl);
        } catch (MqttException e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }

        Radar radar;
        try {
            radar = new Radar(mqttUrl, url);
        } catch (MqttException e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }

        PoliceStation policeStation;
        try {
            policeStation = new PoliceStation(mqttUrl, url);
        } catch (MqttException e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }

        // Declaración de hilos para cada objeto Runnable
        Thread simulatorThread = new Thread(simulator);
        Thread radarThread = new Thread(radar);
        Thread policeStationThread = new Thread(policeStation);

        // Inicio de hilos
        simulatorThread.start();
        radarThread.start();
        policeStationThread.start();
    }
}