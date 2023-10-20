package org.example;

public class Main {
    public static void main(String[] args) {

        Animals rabbit = new Animals("Rabbit", 0.5, 1);
        Animals turtle = new Animals("Turtle", 0.2, 3);
        Animals horse = new Animals("Horse", 0.8, 2);
        Animals dog = new Animals("Dog", 0.3, 4);

        rabbit.start();
        turtle.start();
        horse.start();
        dog.start();

        try {
            rabbit.join();
            turtle.join();
            horse.join();
            dog.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        System.out.println("All animals have finished!");
    }
}