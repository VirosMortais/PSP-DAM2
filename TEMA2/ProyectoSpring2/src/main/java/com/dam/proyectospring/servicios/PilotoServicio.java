package com.dam.proyectospring.servicios;

import com.dam.proyectospring.modelos.Piloto;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interface for the pilot service.
 * This service provides methods for managing pilots.
 */
public interface PilotoServicio {

    /**
     * Retrieves all pilots.
     * @return A list of all pilots.
     */
    List<Piloto> findAllPilotos();

    /**
     * Retrieves a pilot by its ID.
     * @param id The ID of the pilot to retrieve.
     * @return The pilot with the given ID, or null if no such pilot exists.
     */
    Piloto findPilotoById(String id);

    /**
     * Adds a new pilot.
     * @param piloto The pilot to add.
     * @return The added pilot.
     */
    Piloto addPiloto(Piloto piloto);

    /**
     * Modifies an existing pilot.
     * @param id The ID of the pilot to modify.
     * @param piloto The pilot with the new data.
     * @return The modified pilot, or null if no pilot with the given ID exists.
     */
    Piloto modifyPiloto(String id, Piloto piloto);

    /**
     * Deletes a pilot.
     * @param id The ID of the pilot to delete.
     * @return A ResponseEntity indicating the result of the operation.
     */
    ResponseEntity<?> deletePiloto(String id);
}