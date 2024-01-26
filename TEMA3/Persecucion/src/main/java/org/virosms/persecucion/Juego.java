package org.virosms.persecucion;

import javax.swing.JFrame;

public class Juego {

    public static void main(String[] args) {
        Jugador jugador = new Jugador(1);
        JFrame frame = new JFrame("Juego de Persecución");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.add(jugador);
        jugador.requestFocusInWindow();
    }
}