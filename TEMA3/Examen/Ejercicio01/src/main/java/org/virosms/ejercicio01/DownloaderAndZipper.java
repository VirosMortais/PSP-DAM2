package org.virosms.ejercicio01;

import javafx.collections.ListChangeListener;

/**
 * The DownloaderAndZipper class implements the ListChangeListener interface and acts as an observer for the list of UrlEntry objects.
 * It overrides the onChanged method to perform actions when a UrlEntry object is added to the list.
 */
public class DownloaderAndZipper implements ListChangeListener<UrlEntry> {

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
        UrlEntry url = change.getList().getLast();

        if(!url.url().isEmpty()) {
            System.out.println(url.url() + " encolado como " + url.randomString());
        }else {
            System.out.println("Se va a proceder a descargar y comprimir los ficheros");
        }
    }

    /**
     * The createDirectoryIfNotExist method checks if a directory exists at a specified path.
     * If the directory does not exist, it creates the directory.
     * This method is currently commented out and not in use.
     */
    /*private void createDirectoryIfNotExist() throws IOException {
        if(Files.notExists(pathUrl)){
            Files.createDirectory(pathUrl);
        }
    }*/
}