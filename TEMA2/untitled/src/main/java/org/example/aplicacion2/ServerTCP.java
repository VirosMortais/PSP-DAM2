package org.example.aplicacion2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static org.example.aplicacion2.ConstantesTCP.PATH;
import static org.example.aplicacion2.ConstantesTCP.PORT;

public class ServerTCP {
    public static void main(String[] args) throws IOException {


        try (ServerSocket serverSocket = new ServerSocket(PORT)) {


            Path path1 = Path.of(PATH);
            if(Files.notExists(path1)) {
                Files.createFile(path1);
            }

            Socket clientSocket;

            BufferedReader in;
            System.out.println("Server listening on port " + PORT);

            while (true) {
                try {
                    clientSocket = serverSocket.accept();

                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String base64 = in.readLine();

                    if (base64.charAt(0) == base64.charAt(base64.length() - 1) && base64.charAt(0) == '#') {
                        base64 = base64.replace("#", "");
                        String mensaje = new String(Base64.getDecoder().decode(base64));

                        try (BufferedWriter out = Files.newBufferedWriter(path1, UTF_8, APPEND)) {
                            out.write(mensaje);
                            out.newLine();

                        } catch (IOException e) {
                            System.err.println("Error 3: " + e.getMessage());
                            System.exit(-1);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error 2: " + e.getMessage());
                    System.exit(-1);
                }
            }


        } catch (Exception e) {
            System.err.println("Error 1: " + e.getMessage());
            System.exit(-1);
        }
    }
}
