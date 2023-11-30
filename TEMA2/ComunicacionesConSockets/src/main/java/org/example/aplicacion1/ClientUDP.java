package org.example.aplicacion1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import static org.example.aplicacion1.Constastes.HOST;
import static org.example.aplicacion1.Constastes.PORT;


public class ClientUDP {

    public static void main(String[] args) {
        try(DatagramSocket socketUDP = new DatagramSocket()){

            byte[] message = "time".getBytes();
            InetAddress serverHost = InetAddress.getByName(HOST);

            DatagramPacket sendDatagram = new DatagramPacket(message, message.length, serverHost, PORT);

            socketUDP.send(sendDatagram);

            byte[] buffer = new byte[1000];
            DatagramPacket answerDatagram = new DatagramPacket(buffer, buffer.length);
            socketUDP.receive(answerDatagram);

            String received = new String(answerDatagram.getData()).substring(0, answerDatagram.getLength());
            System.out.print("Hora: ");
            System.out.println(received);


        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}
