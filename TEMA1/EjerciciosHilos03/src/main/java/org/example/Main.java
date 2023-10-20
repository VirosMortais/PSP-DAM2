package org.example;

public class Main {
    public static void main(String[] args) {
        Contador contador = new Contador();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                contador.incrementar();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                contador.incrementar();
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                contador.incrementar();
            }
        });

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                contador.incrementar();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(contador.getValor());

    }
}