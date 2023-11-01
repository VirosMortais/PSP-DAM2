package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends Thread {
    Containers containers;

    /**
     * The customer gets a container every second
     */
    public void run() {
        int num;

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Customer interrupted");
            }

            // The customer gets a container
            num = containers.get();

            if(num == -1) {
                break;
            }
            System.out.println("Customer gets " + num);


        }

        System.out.println("Customers finished");

    }
}
