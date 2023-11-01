package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Producer extends Thread{
    Containers containers;

    /**
     * The producer puts a container every second
     */
    public void run() {
        for(int i = 0; i < 10; i++) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
            // The producer puts a container
            containers.put((int) (Math.random() * 100));
        }
        // The producer is finished
        containers.setTerminado(true);
    }
}
