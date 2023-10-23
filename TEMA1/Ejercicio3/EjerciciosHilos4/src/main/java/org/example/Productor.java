package org.example;

public class Productor extends Thread{
    private final Buffer buffer;

    public Productor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++){
            //int valor = (int) (Math.random() * 100);
            System.out.println("Productor " + (i+1));
            buffer.put(i+1);
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                System.out.println("Productor interrumpido" + e.getMessage());
            }
        }
    }
}
