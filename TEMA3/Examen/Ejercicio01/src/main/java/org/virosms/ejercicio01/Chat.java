package org.virosms.ejercicio01;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.virosms.ejercicio01.utils.Utils;

import java.util.Scanner;

public class Chat {
    Scanner sc = new Scanner(System.in);
    ObservableList<UrlEntry> urls = FXCollections.observableArrayList();

    DownloaderAndZipper daz = new DownloaderAndZipper();

    public void showChat(){
        System.out.println("Precione Enter si quieres para de introducir urls");
        System.out.println("Si quieres introducir un url para download insira directamente el url");
        System.out.print("Respuesta: ");
    }

    public void askForUrl(){
        urls.addListener(daz);

        do {
            showChat();
            String url = sc.nextLine();

            urls.add(new UrlEntry(url, Utils.genereteRandomString()));
            if(url.isEmpty()){
                break;
            }
        }while (true);
    }
}
