package org.example;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
public class Containers {
    List<Integer> list;
    private static final int MAX_SIZE = 4;
    private static boolean TERMINADO;

    /**
     * Constructor
     */
    public Containers() {
        this.list = new LinkedList<>();
    }

    /**
     * Get the value of TERMINADO
     */
    public synchronized boolean isTerminado() {
        return TERMINADO;
    }

    /**
     * Set the value of TERMINADO
     */
    public synchronized void setTerminado(boolean terminado) {
        TERMINADO = terminado;
    }

    /**
     * Check if the list is full
     */
    public synchronized boolean isFull() {
        return list.size() == MAX_SIZE;
    }

    /**
     * Check if the list is empty
     */
    public synchronized boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Put a container in the list
     */
    public synchronized void put(int i) {
        // If the list is full, the producer waits
        while (isFull()) {
            try {
                // The producer waits if the list is full
                wait();
            } catch (InterruptedException e) {
                System.out.println("Producer interrupted");
            }
        }
        // The producer puts a container
        list.add(i);
        System.out.println("Producer put " + i);
        // The producer notifies the customer
        notifyAll();
    }

    /**
     * Get a container from the list
     */
    public synchronized int get() {
        if(isTerminado() && isEmpty()){
            return -1;
        }

        // If the list is empty, the customer waits
        while (isEmpty()) {
            try {
                // The customer waits if the list is empty
                wait();
            } catch (InterruptedException e) {
                System.out.println("Customer interrupted");
            }
        }
        // The customer gets a container and notifies the producer and removes the container
        return list.remove(0);
    }
}
