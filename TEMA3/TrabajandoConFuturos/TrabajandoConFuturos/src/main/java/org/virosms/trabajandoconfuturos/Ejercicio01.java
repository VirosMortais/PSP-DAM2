package org.virosms.trabajandoconfuturos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Ejercicio01 {

    public static void main(String[] args) {
        System.out.println("Ejercicio 01");
        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = downloadFile(getUrl());

            CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            futureResponse.thenAccept(Ejercicio01::showInfo);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

    }

    private static HttpRequest downloadFile(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

    }

    private static void showInfo(HttpResponse<String> response){
        System.out.println("Status code: " + response.statusCode());
        System.out.println("Headers response: " + response.headers());
        System.out.println("Body response: " + response.body());
    }

    private static String getUrl(){
        Scanner in = new Scanner(System.in);
        System.out.print("Url: ");
        return in.nextLine();
    }
}
