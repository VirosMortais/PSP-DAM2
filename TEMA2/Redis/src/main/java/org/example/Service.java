package org.example;

import redis.clients.jedis.Jedis;



public class Service implements Runnable{
    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        String urlToShortenKey = "CHARLES:URL_TO_SHORTEN";
        String shortenedUrlKey = "CHARLES:SHORTENED_URL";

        try (Jedis jedis = new Jedis("34.228.162.124", 6379)){
            while (true) {
                String url = jedis.rpop(urlToShortenKey);
                if (url != null) {
                    String shorted = shortUrl(url);
                    jedis.hset(shortenedUrlKey, shorted, url);
                }
            }
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String shortUrl(String url) {
        String[] caracteres = url.split("/");

        StringBuilder shortedUrl = new StringBuilder();
        for (int i = 0; i < (caracteres.length - 1); i++) {
            shortedUrl.append(caracteres[i]).append("/");
        }
        return shortedUrl.toString();
    }
}
