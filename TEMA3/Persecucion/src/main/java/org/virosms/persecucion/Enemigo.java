package org.virosms.persecucion;

import java.util.Observable;
import java.util.Observer;

public class Enemigo implements Observer {
    private int x;
    private int y;
    private Jugador jugador;
    private int pasos = 0;

    public Enemigo(Jugador jugador, int id) {
        this.jugador = jugador;
        do {
            this.x = (int) (Math.random() * 30);
            this.y = (int) (Math.random() * 30);
        } while (Math.abs(this.x - jugador.x) < 5 && Math.abs(this.y - jugador.y) < 5);
    }

    @Override
    public void update(Observable o, Object arg) {
        moverHaciaJugador();
        System.out.println("Enemigo se ha movido a la posición (" + x + "," + y + ").");
        if (x == jugador.x && y == jugador.y) {
            System.out.println("El enemigo ha atrapado al jugador. Fin del juego.");
            System.out.println("Pasos realizados por el jugador: " + jugador.getPasos());
            System.exit(0);
        }
    }

    private void moverHaciaJugador() {
        if (jugador.x > x) {
            x += 2;
        } else if (jugador.x < x) {
            x -= 2;
        }

        if (jugador.y > y) {
            y += 2;
        } else if (jugador.y < y) {
            y -= 2;
        }

        if (x >= 30) {
            x = 30;
        }
        if (x <= 0) {
            x = 0;
        }
        if (y >= 30) {
            y = 30;
        }
        if (y <= 0) {
            y = 0;
        }

        pasos += 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}