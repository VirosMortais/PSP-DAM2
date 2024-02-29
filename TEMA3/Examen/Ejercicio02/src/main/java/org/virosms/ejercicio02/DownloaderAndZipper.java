package org.virosms.ejercicio02;

import javafx.collections.ListChangeListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The DownloaderAndZipper class implements the ListChangeListener interface and acts as an observer for the list of UrlEntry objects.
 * It overrides the onChanged method to perform actions when a UrlEntry object is added to the list.
 */
public class DownloaderAndZipper implements ListChangeListener<UrlEntry> {


    static Path pathUrl = Paths.get("src/web");

    /**
     * The createDirectoryIfNotExist method checks if a directory exists at a specified path.
     * If the directory does not exist, it creates the directory.
     * This method is currently commented out and not in use.
     */
    private static void createDirectoryIfNotExist() throws IOException {
        if (Files.notExists(pathUrl)) {
            Files.createDirectory(pathUrl);
        }
    }

    public static CompletableFuture<Void> downloadFile(UrlEntry urlEntry) {
        String url;
        if (!urlEntry.url().startsWith("http://") && !urlEntry.url().startsWith("https://")) {
            // Prepend "http://" to the URL if it doesn't start with a protocol
            url = "https://" + urlEntry.url();
        } else {
            url = urlEntry.url();
        }
        return CompletableFuture.runAsync(() -> {
            try {
                createDirectoryIfNotExist();
                try (HttpClient client = HttpClient.newHttpClient()) {
                    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    String name = "File_" + urlEntry.randomString() + ".html";
                    Path file = pathUrl.resolve(name);
                    Files.writeString(file, response.body(), StandardCharsets.UTF_8);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });
    }

    public static CompletableFuture<Void> zipFiles(List<UrlEntry> list) {
        return CompletableFuture.runAsync(() -> {
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(pathUrl + "/web.zip"))) {
                Files.walk(pathUrl)
                        .filter(Files::isRegularFile)  // Add this line
                        .forEach(path -> {
                            ZipEntry zipEntry = new ZipEntry(path.getFileName().toString());
                            try {
                                zos.putNextEntry(zipEntry);
                                Files.copy(path, zos);
                                zos.closeEntry();
                            } catch (IOException e) {
                                throw new UncheckedIOException(e);
                            }
                        });
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    /**
     * The onChanged method is called when a UrlEntry object is added to the list.
     * It retrieves the last UrlEntry object from the list and checks if its URL is empty.
     * If the URL is not empty, it prints a message to the console with the URL and the random string.
     * If the URL is empty, it prints a different message to the console.
     *
     * @param change A Change object representing the change that occurred in the list.
     */
    @Override
    public void onChanged(Change<? extends UrlEntry> change) {
        UrlEntry entry = change.getList().getLast();


        if (!entry.url().isEmpty()) {
            System.out.println(entry.url() + " encolado como " + entry.randomString());
        }
    }

}