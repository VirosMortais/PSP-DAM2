package org.virosms.ejercicio02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.virosms.ejercicio02.utils.Utils;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * The Chat class is responsible for interacting with the user and managing the list of URLs.
 * It reads URLs from the user, generates a random string for each URL, and adds them to an observable list.
 * It also adds a listener to the list to trigger actions when a URL is added.
 */
public class Chat {
    // Scanner object for reading user input
    Scanner sc = new Scanner(System.in);
    // Observable list of UrlEntry objects
    ObservableList<UrlEntry> urls = FXCollections.observableArrayList();

    // DownloaderAndZipper object that acts as a listener for the urls list
    DownloaderAndZipper daz = new DownloaderAndZipper();

    /**
     * The showChat method displays instructions to the user.
     */
    public void showChat() {
        System.out.println("Precione Enter si quieres para de introducir urls");
        System.out.println("Si quieres introducir un url para download insira directamente el url");
        System.out.print("Respuesta: ");
    }

    /**
     * The askForUrl method reads URLs from the user and adds them to the urls list.
     * It also generates a random string for each URL and creates a UrlEntry object.
     * The method continues to read URLs until the user enters an empty string.
     */
    public void askForUrl() {
        // Add the DownloaderAndZipper object as a listener to the urls list
        urls.addListener(daz);

        do {
            // Display instructions to the user
            showChat();
            // Read the URL from the user
            String url = sc.nextLine();

            // Add a new UrlEntry object to the urls list
            if(!url.isEmpty()) {
                urls.add(new UrlEntry(url, Utils.genereteRandomString()));
            }
            // Break the loop if the user entered an empty string
            if (url.isEmpty()) {

                List<CompletableFuture<Void>> futures = urls.stream()
                        .map(DownloaderAndZipper::downloadFile)
                        .toList();

                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                        .thenCompose(v -> DownloaderAndZipper.zipFiles(urls.stream().toList()))
                        .join();
                System.out.println("Se va a proceder a descargar y comprimir los ficheros");
                break;

            }
        } while (true);
    }
}