package org.example;

import lombok.Data;

@Data
public class Animals extends Thread{
    private final String animalName;
    private final double probabilityFail;

    public Animals(String animalName, double probabilityFail, int priority) {
        this.animalName = animalName;
        this.probabilityFail = probabilityFail;
        this.setPriority(priority);
    }
    @Override
    public void run() {
        for(int i = 0; i < 1000; i++) {
            if(Math.random() < probabilityFail) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println(animalName + " has finished!");
    }


}
