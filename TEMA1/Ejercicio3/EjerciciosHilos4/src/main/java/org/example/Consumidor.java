package org.example;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Consumidor extends Thread {
    private Buffer buffer;

    public Consumidor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int valor;
        for(int i = 0; i < 10; i++){
            valor = buffer.get();
            System.out.println("Consumidor " + valor);
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                System.out.println("Consumidor interrumpido" + e.getMessage());
            }
        }
    }

}
