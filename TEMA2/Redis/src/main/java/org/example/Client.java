package org.example;


import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Client {
    public static void main(String[] args) throws IOException {

        String urlToShortenKey = "CHARLES:URL_TO_SHORTEN";
        String shortenedUrlKey = "CHARLES:SHORTENED_URL";

        Thread thread = new Thread(new Service());


        try (Jedis jedis = new Jedis("34.228.162.124", 6379)) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Por favor, ingresa el comando que deseas ejecutar:\n" +
                    "- shorten URL : Donde URL es la dirección web que deseas acortar\n" +
                    "- url SHORTEDURL : Donde SHORTEDURL es la dirección web acortada que deseas expandir\n" +
                    "- exit : Para salir del programa");
            System.out.print("Por favor, ingresa tu comando:");

            String command = reader.readLine();

            thread.start();

            jedis.del(urlToShortenKey);
            while (!command.equals("exit")) {
                String[] commandSplitted = command.split(" ");

                switch (commandSplitted[0]) {

                    case "shorten":

                        String url = commandSplitted[1];

                        jedis.lpush(urlToShortenKey, url);

                        try {
                            System.out.println("La url se esta acortando, por favor espera un momento");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case "url":
                        String shortedUrl = commandSplitted[1];
                        String urlLarge = jedis.hget(shortenedUrlKey, shortedUrl);

                        if (urlLarge == null)
                            System.out.println("La url no existe");
                        else
                            System.out.println("La url es : " + urlLarge);

                        break;
                    default:
                        System.out.println("Comando no reconocido");
                        break;
                }

                System.out.print("Escribe el comando que quieras :");
                command = reader.readLine();
            }

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }finally {
            thread.interrupt();
        }
    }


}