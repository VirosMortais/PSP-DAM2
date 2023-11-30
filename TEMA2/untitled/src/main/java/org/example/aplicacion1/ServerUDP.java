package org.example.aplicacion1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;
import java.net.SocketException;

import static org.example.aplicacion1.Constastes.PORT;
public class ServerUDP {

    public static void main(String[] args) {
        try(DatagramSocket socketUDP = new DatagramSocket(PORT)){


            byte[] buffer = new byte[1000];

            while(true){
                DatagramPacket receivedDatagram = new DatagramPacket(buffer, buffer.length);

                socketUDP.receive(receivedDatagram);

                String received = new String(receivedDatagram.getData()).substring(0, 4);

                if(received.compareTo("time") == 0){
                    String time = String.format("%02d:%02d", LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
                    System.out.printf("Sending response: %s", time);
                    System.out.println();
                    DatagramPacket responseDatagram = new DatagramPacket(time.getBytes(), time.length(), receivedDatagram.getAddress(), receivedDatagram.getPort());
                    socketUDP.send(responseDatagram);
                }
            }

        }catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (java.io.IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}