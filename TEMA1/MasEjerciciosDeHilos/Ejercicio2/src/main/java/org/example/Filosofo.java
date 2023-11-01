package org.example;

import java.util.Random;

public class Filosofo extends Thread{
    private Palillos palillo;
    private int palilloDerecho;
    private int palilloIzquierdo;

    public Filosofo(Palillos palillo, int palilloDerecho, int palilloIzquierdo){
        this.palillo = palillo;
        this.palilloDerecho = palilloDerecho;
        this.palilloIzquierdo = palilloIzquierdo;
    }

    public void run(){
        while(true){
            boolean palillosTomados;
            palillosTomados = palillo.tomarPalillos(palilloIzquierdo, palilloDerecho);
            if (palillosTomados) {
                comer();
                palillo.soltarPalillos(palilloIzquierdo, palilloDerecho);
                dormir();
            }
        }
    }

    private void randomTime() {
        Random generador = new Random();
        int secs = generador.nextInt(3);
        try {
            Thread.sleep(secs);
        } catch (InterruptedException e) {
            System.out.println("Fallo la espera");
        }
    }

    private void comer() {
        System.out.println("Filosofo " + Thread.currentThread().getName() + " comiendo");
        randomTime();
    }

    private void dormir() {
        System.out.println("Filosofo " + Thread.currentThread().getName() + " durmiendo ");
        randomTime();
    }
}
