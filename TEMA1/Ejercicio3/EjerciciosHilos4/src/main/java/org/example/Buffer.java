package org.example;

import lombok.Data;

@Data
public class Buffer {
    private boolean empty;
    private int valor;

    public Buffer() {
        this.empty = true;
    }

    public synchronized void put(int valor){
        while (!empty){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Productor interrumpido" + e.getMessage());
            }
        }
        this.valor = valor;
        empty = false;
        notifyAll();
    }

    public synchronized int get(){
        while (empty){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Consumidor interrumpido" + e.getMessage());
            }
        }
        empty = true;
        notifyAll();
        return valor;
    }
}
