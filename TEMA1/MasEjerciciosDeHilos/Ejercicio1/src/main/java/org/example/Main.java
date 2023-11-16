package org.example;


public class Main {
    public static void main(String[] args)  {
        Containers containers = new Containers();
        Producer producer1 = new Producer(containers);
        Producer producer2 = new Producer(containers);
        Producer producer3 = new Producer(containers);


        Customer customer1 = new Customer(containers);
        Customer customer2 = new Customer(containers);

        producer1.start();
        producer2.start();
        producer3.start();

        customer1.start();
        customer2.start();


        try {
            producer1.join();
            producer2.join();
            producer3.join();

            customer1.join();
            customer2.join();
        } catch (InterruptedException e) {
            System.out.println("Main interrupted");
        }
        System.out.println("Main finished");
        System.exit(0);

    }
}