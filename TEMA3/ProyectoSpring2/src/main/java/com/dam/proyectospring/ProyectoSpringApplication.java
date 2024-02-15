package com.dam.proyectospring;

import com.dam.proyectospring.modelos.Piloto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;

/**
 * Main class for the Spring Boot application.
 */
@SpringBootApplication
public class ProyectoSpringApplication {
    public static void main(String[] args) {
        // Start the Spring Boot application
        SpringApplication.run(ProyectoSpringApplication.class, args);
        // Create a WebClient instance for making HTTP requests
        final WebClient webClient = WebClient.create("http://localhost:8080/api");

        int options;
        do {
            // Print the menu options
            printMenu();
            // Read the user's option
            options = readOption();

            // Perform an action based on the user's option
            switch (options) {
                case 1:
                    // Add a new user
                    System.out.println("Añadir un nuevo usuario");
                    webClient.post().uri("/pilotos").contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(readPiloto())).retrieve().bodyToMono(String.class).subscribe(System.out::println);
                    break;
                case 2:
                    // Show all users
                    System.out.println("Mostrar todos los usuarios");
                    webClient.get().uri("/pilotos").retrieve().bodyToFlux(String.class).subscribe(System.out::println);
                    break;
                case 3:
                    // Search for a user
                    System.out.println("Buscar un usuario");
                    webClient.get().uri("/pilotos/" + readIdPiloto()).retrieve().bodyToMono(String.class).subscribe(System.out::println);
                    break;
                case 4:
                    // Modify a user
                    System.out.println("Modificar un usuario");
                    webClient.put().uri("/pilotos/" + readIdPiloto()).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(readPiloto())).retrieve().bodyToMono(String.class).subscribe(System.out::println);
                    break;
                case 5:
                    // Delete a user
                    System.out.println("Eliminar un usuario");
                    webClient.delete().uri("/pilotos/" + readIdPiloto()).retrieve().bodyToMono(String.class).subscribe(System.out::println);
                    break;
                case 6:
                    // Exit the application
                    System.out.println("Salir");
                    break;
                default:
                    // Invalid option
                    System.out.println("Opción no válida");
                    break;
            }

        }while (options != 6);
    }

    /**
     * Reads the details of a pilot from the user's input.
     * @return A Piloto object with the details entered by the user.
     */
    static Piloto readPiloto() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Piloto piloto = new Piloto();
        try {
            System.out.println("Introduce el nombre del piloto");
            piloto.setNombre(in.readLine());
            System.out.println("Introduce el abreviatura del piloto");
            piloto.setAbreviatura(in.readLine());
            System.out.println("Introduce el número del piloto");
            piloto.setNumero(Integer.parseInt(in.readLine()));
            System.out.println("Introduce el equipo del piloto");
            piloto.setEquipo(in.readLine());
            System.out.println("Introduce el país del piloto");
            piloto.setPais(in.readLine());
            System.out.println("Introduce la fecha de nacimiento del piloto");
            piloto.setFechaNacimiento(LocalDate.parse(in.readLine()));
        }catch (Exception e) {
            System.out.println("Error al leer los datos del piloto");
        }
        return piloto;
    }

    /**
     * Reads the ID of a pilot from the user's input.
     * @return The ID entered by the user.
     */
    static String readIdPiloto(){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String id = "";
        try {
            System.out.println("Introduce el id del piloto");
            id = in.readLine();
        } catch (Exception e) {
            System.out.println("Error al leer el id del piloto");
        }
        return id;
    }

    /**
     * Prints the menu options to the console.
     */
    static void printMenu(){
        System.out.println("1. Añadir un nuevo usuario");
        System.out.println("2. Mostrar todos los usuarios");
        System.out.println("3. Buscar un usuario");
        System.out.println("4. Modificar un usuario");
        System.out.println("5. Eliminar un usuario");
        System.out.println("6. Salir");
    }

    /**
     * Reads the user's menu option from the console.
     * @return The option entered by the user.
     */
    static int readOption(){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int option = 0;
        try {
            option = Integer.parseInt(in.readLine());
        } catch (Exception e) {
            System.out.println("Error al leer la opción");
        }
        return option;
    }
}