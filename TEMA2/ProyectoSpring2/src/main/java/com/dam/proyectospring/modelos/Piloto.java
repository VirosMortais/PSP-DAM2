package com.dam.proyectospring.modelos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a Pilot in the system.
 * This class is a MongoDB document and can be serialized.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("pilotos")
public class Piloto implements Serializable {
    /**
     * The unique ID of the pilot.
     */
    @Id
    private String id;

    /**
     * The name of the pilot.
     */
    @Field("driver")
    private String nombre;

    /**
     * The abbreviation of the pilot's name.
     */
    @Field("abbreviation")
    private String abreviatura;

    /**
     * The number associated with the pilot.
     */
    @Field("no")
    private int numero;

    /**
     * The team that the pilot is part of.
     */
    @Field("team")
    private String equipo;

    /**
     * The country of the pilot.
     */
    @Field("country")
    private String pais;

    /**
     * The date of birth of the pilot.
     */
    @Field("date_of_birth")
    private LocalDate fechaNacimiento;
}