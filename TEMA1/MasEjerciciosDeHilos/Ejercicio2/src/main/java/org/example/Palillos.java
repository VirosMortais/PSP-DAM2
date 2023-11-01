package org.example;

public class Palillos {
    boolean[] palilloLibre;

    public Palillos(){
        palilloLibre = new boolean[5];
        for (int i = 0; i < 5; i++){
            palilloLibre[i] = true;
        }
    }

    public synchronized boolean tomarPalillos(int palilloDerecho, int palilloIzquierdo){
        boolean seConsigue = false;
        if ((palilloLibre[palilloDerecho]) && (palilloLibre[palilloIzquierdo])) {
            palilloLibre[palilloDerecho] = false;
            palilloLibre[palilloIzquierdo] = false;
            seConsigue = true;
        } //Fin del if
        return seConsigue;
    }

    public void soltarPalillos(int palilloDerecho, int palilloIzquierdo) {
        palilloLibre[palilloDerecho] = true;
        palilloLibre[palilloIzquierdo] = true;
    }
}
