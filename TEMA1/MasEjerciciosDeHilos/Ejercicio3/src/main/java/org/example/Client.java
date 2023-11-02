package org.example;

import java.util.Random;

public class Client extends Thread{

    private Door door;
    private Container container;
    private String name;
    private Random random;
    private final int MAX_TRIES = 10;

    public Client(Door door, Container container, String name) {
        this.door = door;
        this.container = container;
        this.name = name;
        this.random = new Random();
    }

    public void waiting(){
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        for(int i = 0; i < MAX_TRIES; i++){
            if(!door.isOcupaied()) {
                if(door.occupyDoor()){
                    waiting();
                    door.releaseDoor();
                    if(container.getProduct()) {
                        System.out.println("Client " + name + " got a product");
                        return;
                    }else {
                        System.out.println("Client " + name + " didn't get a product");
                        return;
                    }
                }
            } else {
                waiting();
            }
        }

        System.out.println("Client " + name + ": i give up");
    }


}
