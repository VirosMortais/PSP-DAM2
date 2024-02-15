package com.dam.proyectospring.servicios;

import com.dam.proyectospring.modelos.Piloto;
import com.dam.proyectospring.repositorios.PilotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing pilots.
 */
@Service
public class PilotoServicioImpl implements PilotoServicio {
    /**
     * The repository for accessing the pilots in the database.
     */
    @Autowired
    private PilotoRepositorio pilotoRepositorio;

    /**
     * Returns a list of all pilots.
     * @return A list of all pilots.
     */
    @Override
    public List<Piloto> findAllPilotos() {
        return pilotoRepositorio.findAll();
    }

    /**
     * Returns a pilot with the given ID.
     * @param id The ID of the pilot.
     * @return The pilot with the given ID, or null if no such pilot exists.
     */
    @Override
    public Piloto findPilotoById(String id) {
        return pilotoRepositorio.findById(id).orElse(null);
    }

    /**
     * Adds a new pilot.
     * @param piloto The pilot to add.
     * @return The added pilot.
     */
    @Override
    public Piloto addPiloto(Piloto piloto) {
        return pilotoRepositorio.save(piloto);
    }

    /**
     * Modifies an existing pilot.
     * @param id The ID of the pilot to modify.
     * @param piloto The pilot with the new data.
     * @return The modified pilot, or null if no pilot with the given ID exists.
     */
    @Override
    public Piloto modifyPiloto(String id, Piloto piloto) {
        Piloto pilotoAModificar = pilotoRepositorio.findById(id).orElse(null);

        if(pilotoAModificar == null)
            return null;

        return addPiloto(piloto);
    }

    /**
     * Deletes a pilot.
     * @param id The ID of the pilot to delete.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Override
    public ResponseEntity<?> deletePiloto(String id) {

        try{
            pilotoRepositorio.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}