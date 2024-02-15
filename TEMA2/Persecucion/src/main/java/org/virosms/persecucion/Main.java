package org.virosms.persecucion;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import java.util.Random;

public class Main extends Application  implements KeyListener{
    private Jugador jugador;
    private ObservableList<Enemigo> enemigos;
    private TextArea outputTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        jugador = new Jugador(generarCoordenadasAleatorias());
        enemigos = FXCollections.observableArrayList();
        enemigos.add(new Enemigo(generarCoordenadasAleatorias()));
        enemigos.forEach(enemigo -> enemigo.moverHacia(jugador.getCoordenadas()));

        BorderPane root = new BorderPane();
        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        root.setCenter(outputTextArea);
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Juego de Evitar al Enemigo");
        primaryStage.show();

        mostrarEstadoInicial();
    }

    private void mostrarEstadoInicial() {
        outputTextArea.appendText("Posición inicial del jugador: " + jugador.getCoordenadas() + "\n");
        enemigos.forEach(enemigo -> outputTextArea.appendText("El enemigo se ha movido a la posición " + enemigo.getCoordenadas() + "\n"));
    }

    private void mostrarEstado() {
        outputTextArea.appendText("El jugador se ha movido a la posición " + jugador.getCoordenadas() + "\n");
        enemigos.forEach(enemigo -> outputTextArea.appendText("El enemigo se ha movido a la posición " + enemigo.getCoordenadas() + "\n"));
    }

    private void verificarColision() {
        enemigos.forEach(enemigo -> {
            if (jugador.getCoordenadas().equals(enemigo.getCoordenadas())) {
                outputTextArea.appendText("¡El enemigo ha capturado al jugador! Pasos realizados: " + jugador.getPasos() + "\n");
                System.exit(0);
            }
        });
    }

    private Coordenadas generarCoordenadasAleatorias() {
        Random random = new Random();
        int x = random.nextInt(26) + 5; // Entre 5 y 30
        int y = random.nextInt(26) + 5; // Entre 5 y 30
        return new Coordenadas(x, y);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                jugador.moverArriba();
                break;
            case KeyEvent.VK_S:
                jugador.moverAbajo();
                break;
            case KeyEvent.VK_A:
                jugador.moverIzquierda();
                break;
            case KeyEvent.VK_D:
                jugador.moverDerecha();
                break;
        }

        enemigos.forEach(enemigo -> enemigo.moverHacia(jugador.getCoordenadas()));
        mostrarEstado();
        verificarColision();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

class Coordenadas {
    private int x;
    private int y;

    public Coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Coordenadas otra) {
        return this.x == otra.x && this.y == otra.y;
    }
}

class Jugador {
    private Coordenadas coordenadas;
    private int pasos;

    public Jugador(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
        this.pasos = 0;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public int getPasos() {
        return pasos;
    }

    public void moverIzquierda() {
        coordenadas = new Coordenadas(coordenadas.getX() - 1, coordenadas.getY());
        pasos++;
        System.out.println("Jugador se movió a la izquierda a la posición: " + coordenadas);
    }

    public void moverDerecha() {
        coordenadas = new Coordenadas(coordenadas.getX() + 1, coordenadas.getY());
        pasos++;
        System.out.println("Jugador se movió a la Derecha a la posición: " + coordenadas);
    }

    public void moverArriba() {
        coordenadas = new Coordenadas(coordenadas.getX(), coordenadas.getY() - 1);
        pasos++;
        System.out.println("Jugador se movió a la Arriba a la posición: " + coordenadas);
    }

    public void moverAbajo() {
        coordenadas = new Coordenadas(coordenadas.getX(), coordenadas.getY() + 1);
        pasos++;
        System.out.println("Jugador se movió a la Abajo a la posición: " + coordenadas);
    }
}

class Enemigo {
    private Coordenadas coordenadas;

    public Enemigo(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void moverHacia(Coordenadas jugadorCoordenadas) {
        // Mover dos posiciones hacia el jugador en la dirección que lo acerque más
        int deltaX = jugadorCoordenadas.getX() - coordenadas.getX();
        int deltaY = jugadorCoordenadas.getY() - coordenadas.getY();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0) {
                coordenadas = new Coordenadas(coordenadas.getX() + 2, coordenadas.getY());
            } else {
                coordenadas = new Coordenadas(coordenadas.getX() - 2, coordenadas.getY());
            }
        } else {
            if (deltaY > 0) {
                coordenadas = new Coordenadas(coordenadas.getX(), coordenadas.getY() + 2);
            } else {
                coordenadas = new Coordenadas(coordenadas.getX(), coordenadas.getY() - 2);
            }
        }
        System.out.println("Enemigo se movió hacia el jugador a la posición: " + coordenadas);
    }
}
