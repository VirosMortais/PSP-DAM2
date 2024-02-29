package org.virosms.ejercicio01;

import javafx.collections.ListChangeListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloaderAndZipper implements ListChangeListener<UrlEntry> {

    //Path pathUrl = Paths.get("src/webs");
    @Override
    public void onChanged(Change<? extends UrlEntry> change) {
        UrlEntry url = change.getList().getLast();

        if(!url.getUrl().isEmpty()) {
            System.out.println(url.getUrl() + " encolado como " + url.getRandomString());
        }else {
            System.out.println("Se va a proceder a descargar y comprimir los ficheros");
        }

    }

    /*private void createDirectoryIfNotExist() throws IOException {
        if(Files.notExists(pathUrl)){
            Files.createDirectory(pathUrl);
        }
    }*/
}
