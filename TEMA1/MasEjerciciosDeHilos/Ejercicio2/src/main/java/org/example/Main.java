package org.example;
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Palillos palillos = new Palillos();

        Filosofo filosofo1 = new Filosofo(palillos, 0, 1);
        Filosofo filosofo2 = new Filosofo(palillos, 1, 2);
        Filosofo filosofo3 = new Filosofo(palillos, 2, 0);
        Filosofo filosofo4 = new Filosofo(palillos, 3, 4);

        filosofo1.setName("1");
        filosofo2.setName("2");
        filosofo3.setName("3");
        filosofo4.setName("4");

        filosofo1.start();
        filosofo2.start();
        filosofo3.start();
        filosofo4.start();

        filosofo1.join();
        filosofo2.join();
        filosofo3.join();
        filosofo4.join();


    }
}