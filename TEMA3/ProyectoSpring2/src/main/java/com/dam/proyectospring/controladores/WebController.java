package com.dam.proyectospring.controladores;

import com.dam.proyectospring.modelos.Piloto;
import com.dam.proyectospring.servicios.PilotoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller class for the web interface.
 * This class handles the mapping of the root URL ("/") to the index view.
 */
@Controller
public class WebController {
    /**
     * The service for managing pilots.
     */
    @Autowired
    private PilotoServicio pilotoServicio;

    /**
     * Maps the root URL ("/") to the index view.
     * The index view displays a list of all pilots.
     * @param model The model to add attributes to for the view.
     * @return The name of the view to render.
     */
    @RequestMapping(value ="/")
    public String index(Model model) {
        List<Piloto> pilotos = pilotoServicio.findAllPilotos();
        model.addAttribute("pilotos", pilotos);
        return "index";
    }
}
