package org.virosms.ejercicio02;

import javafx.collections.ListChangeListener;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Downloader implements ListChangeListener<String> {

    Path pathURL = Paths.get("src/webs");
    @Override
    public void onChanged(Change<? extends String> change) {

        String url = change.getList().getLast();

        // Descargar archivo
        try (HttpClient client = HttpClient.newHttpClient()){
            if(Files.notExists(pathURL)){
                Files.createDirectory(pathURL);
            }

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String name = "File_"+change.getList().size()+".html";

            Path file = pathURL.resolve(name);

            Files.writeString(file, response.body(), StandardCharsets.UTF_8);


            System.out.println("Se ha iniciado la descarga del archivo URL " + change.getList().getLast());
            System.out.println("Descargando archivo...\n");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }


    }
}
