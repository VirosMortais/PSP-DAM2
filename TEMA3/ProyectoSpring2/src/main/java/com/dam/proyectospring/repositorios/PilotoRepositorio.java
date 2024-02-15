package com.dam.proyectospring.repositorios;

import com.dam.proyectospring.modelos.Piloto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository interface for managing pilots in the MongoDB database.
 * This interface extends the MongoRepository interface provided by Spring Data MongoDB.
 */
public interface PilotoRepositorio extends MongoRepository<Piloto, String> {

    /**
     * Finds a pilot by their name.
     * @param nombre The name of the pilot to find.
     * @return The pilot with the given name, or null if no such pilot exists.
     */
    Piloto findByNombre(String nombre);

    /**
     * Finds pilots whose names contain a given string.
     * @param nombre The string to search for in the pilots' names.
     * @return A list of pilots whose names contain the given string.
     */
    List<Piloto> findByNombreContaining(String nombre);

    /**
     * Finds pilots from a specific team and sorts them by their number.
     * @param equipo The team of the pilots to find.
     * @return A list of pilots from the given team, sorted by their number.
     */
    List<Piloto> findByEquipoOrderByNumero(String equipo);
}