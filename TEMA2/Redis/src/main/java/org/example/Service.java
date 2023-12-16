package org.example;

import redis.clients.jedis.Jedis;



import redis.clients.jedis.Jedis;

import java.util.Random;

import static org.example.Constantes.*;

/**
 * La clase Service implementa Runnable y se utiliza para acortar URLs.
 * Se conecta a Redis y en un bucle infinito, intenta obtener una URL de la lista de URLs para acortar.
 * Si hay una URL disponible, genera una URL acortada y la almacena en una tabla hash en Redis.
 * Luego imprime un mensaje indicando que la URL ha sido acortada.
 * Si ocurre una excepción durante la ejecución, imprime el mensaje de error.
 *
 * La clase contiene tres métodos principales:
 * - run(): Este método se ejecuta cuando se inicia el hilo.
 * - print(String message): Este método imprime un mensaje en la consola.
 * - shortUrl(): Este método genera una cadena aleatoria de 8 caracteres utilizando letras mayúsculas, minúsculas y números.
 *
 * Además, la clase contiene tres constantes:
 * - URL_TO_SHORTEN_KEY: La clave utilizada para almacenar las URLs a acortar en Redis.
 * - SHORTENED_URL_KEY: La clave utilizada para almacenar las URLs acortadas en Redis.
 * - ALLOWED_CHARACTERS: Los caracteres permitidos para generar la cadena aleatoria de la URL acortada.
 *
 * @author Charles
 * @version 1.0
 * @since 1.0
 */
public class Service implements Runnable{
    private static final String URL_TO_SHORTEN_KEY = "CHARLES:URLS_TO_SHORT";
    private static final String SHORTENED_URL_KEY = "CHARLES:SHORTED_URLS";
    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final String PERSONAL_DOMAIN = "charles.com/";


    /**
     * Este método se ejecuta cuando se inicia el hilo. Se conecta a Redis y entra en un bucle infinito.
     * En cada iteración del bucle, intenta obtener una URL de la lista de URLs para acortar.
     * Si hay una URL disponible, genera una URL acortada y la almacena en una tabla hash en Redis.
     * Luego imprime un mensaje indicando que la URL ha sido acortada.
     * Si ocurre una excepción durante la ejecución, imprime el mensaje de error.
     */
    @Override
    public void run() {
        try (Jedis jedis = new Jedis(HOST, PORT)){
            while (true) {
                String url = jedis.lpop(URL_TO_SHORTEN_KEY);
                if (url != null) {
                    String shorted = shortUrl();
                    jedis.hset(SHORTENED_URL_KEY, shorted, url);
                    print("La URL " + url + " ha sido acortada a " + PERSONAL_DOMAIN + shorted);
                }
            }
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Imprime un mensaje en la consola.
     *
     * @param message El mensaje que se va a imprimir.
     */
    private static void print(String message){
        System.out.println(message);
    }

    /**
     * Genera una cadena aleatoria de 8 caracteres utilizando letras mayúsculas, minúsculas y números.
     * Esta cadena se utiliza para representar una URL acortada.
     *
     * @return Una cadena aleatoria de 8 caracteres.
     */
    private static String shortUrl() {
        Random random = new Random();
        StringBuilder shortedUrl = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            shortedUrl.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        }
        return shortedUrl.toString();
    }
}
