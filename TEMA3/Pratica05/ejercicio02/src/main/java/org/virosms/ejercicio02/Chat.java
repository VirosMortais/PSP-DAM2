package org.virosms.ejercicio02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Scanner;

public class Chat {

    Downloader downloader = new Downloader();
    Scanner sc = new Scanner(System.in);

    ObservableList<String> urls = FXCollections.observableArrayList();

    public void showChat() {
        System.out.println("Si quieres parar pulsa 1");
        System.out.println("Si quieres introducir un url para download insira directamente el url");
        System.out.print("Respuesta: ");
    }

    public void askForUrl() {
        urls.addListener(downloader);

        do{
            showChat();
            String url = sc.nextLine();
            if (url.equals("1")) {
                break;
            }
            urls.add(url);
        } while (true);

    }
}
