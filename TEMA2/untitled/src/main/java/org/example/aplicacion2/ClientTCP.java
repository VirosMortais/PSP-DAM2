package org.example.aplicacion2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static org.example.aplicacion2.ConstantesTCP.HOST;
import static org.example.aplicacion2.ConstantesTCP.PORT;

public class ClientTCP {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        try(Socket clientSocket = new Socket(HOST, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true)){
                System.out.println("Client connected to " + HOST + ":" + PORT);

            System.out.println("Introduce un mensaje: ");
            String mensaje = scanner.nextLine();
            String base64 = String.format("#%s#", new String(java.util.Base64.getEncoder().encode(mensaje.getBytes())));

            out.println(base64);
        }catch (Exception e){
            System.err.println("Error 1: " + e.getMessage());
            System.exit(-1);
        }
    }
}
