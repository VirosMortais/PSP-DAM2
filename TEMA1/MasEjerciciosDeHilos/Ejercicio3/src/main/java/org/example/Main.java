package org.example;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        final int MAX_CLIENTS = 100;
        final int MAX_CAPACITY = 20;

        LinkedList<Client> clients = new LinkedList<>();
        Door door = new Door();
        Container containers = new Container(MAX_CAPACITY);



        for(int i = 0; i < MAX_CLIENTS; i++){
            clients.add(new Client(door,  containers, "Client " + (i+1)));
        }

        for(Client client : clients){
            client.start();
        }

        for(Client client : clients){
            try {
                client.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}