package org.virosms.ejercicio03.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.virosms.ejercicio03.model.DateEntity;
import org.virosms.ejercicio03.service.DateService;

@RestController
@RequestMapping("/api")
public class DateController {

    DateService dateService;

    public DateController(DateService dateService) {
        this.dateService = dateService;
    }

    @GetMapping("/date")
    public ResponseEntity<DateEntity> getDate() {
        return dateService.getDate();
    }

    @GetMapping("/date/{n}")
    public ResponseEntity<DateEntity> getDatePlusDays(@PathVariable int n) {
        return dateService.getDatePlusDays(n);
    }

    @PostMapping("/date")
    public ResponseEntity<DateEntity> changeDate(@RequestBody DateEntity dateEntity) {
        return dateService.changeDate(dateEntity);
    }
}
