package com.dam.proyectospring.controladores;

import com.dam.proyectospring.modelos.Piloto;
import com.dam.proyectospring.servicios.PilotoServicio;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class APIController {
    @Autowired
    private PilotoServicio pilotoServicio;

    // GET de todos los pilotos, devuelve un JSON con todos los pilotos
    @GetMapping(value = "/api/pilotos")
    public ResponseEntity<List<Piloto>> getProduct()
    {
        List<Piloto> pilotos = pilotoServicio.findAllPilotos();
        return new ResponseEntity<>(pilotos, HttpStatus.OK);
    }

    // GET de un piloto, devuelve un JSON del piloto
    @GetMapping("/piloto/{id}")
    public ResponseEntity<Piloto> getProduct(@PathVariable String id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Piloto piloto = pilotoServicio.findPilotoById(id);

        if (piloto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // TODO buscar el piloto con identificador id
        return new ResponseEntity<>(piloto, HttpStatus.OK);
    }

    // POST de un piloto, crea un piloto
    @PostMapping("/pilotos")
    public ResponseEntity<Piloto> addProduct(@RequestBody Piloto piloto) {
        if(piloto == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Piloto nuevoPiloto = pilotoServicio.addPiloto(piloto);
        if(nuevoPiloto == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // TODO Crear un nuevo pilot en la BB.DD. La información del nuevo piloto se encuentra en el objeto piloto
        // Para que llegue esta información en RESTED tenéis que poner en el body un JSON con la información del piloto a crear
        return new ResponseEntity<>(nuevoPiloto, HttpStatus.OK);
    }

    // PUT de un piloto, actualiza un piloto
    @PutMapping("/pilotos/{id}")
    public ResponseEntity<Piloto> modifyProduct(@PathVariable String id, @RequestBody Piloto piloto) {
        Piloto response = pilotoServicio.modifyPiloto(id, piloto);

        if(response == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // TODO Actualizar un nuevo pilot en la BB.DD. La información del nuevo piloto se encuentra en el objeto piloto
        // Para que llegue esta información en RESTED tenéis que poner en el body un JSON con la información del piloto a crear

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        // TODO Borrar un producto
        return pilotoServicio.deletePiloto(id);
    }

}
