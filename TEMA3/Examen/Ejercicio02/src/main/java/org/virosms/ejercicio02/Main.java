package org.virosms.ejercicio02;

/**
 * The Main class is the entry point of the application.
 * It creates an instance of the Chat class and starts the URL input process.
 */
public class Main {
    /**
     * The main method is the entry point of the Java application.
     * It creates an instance of the Chat class and calls the askForUrl method to start the URL input process.
     *
     * @param args An array of command-line arguments for the application.
     */
    public static void main(String[] args) {
        // Create a new Chat instance
        Chat chat = new Chat();

        // Start the URL input process
        chat.askForUrl();
    }
}