package org.virosms.ejercicio03.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.virosms.ejercicio03.entity.RandomNum;

import java.util.Random;

@RestController
@RequestMapping("/random")
public class RandomController {

    private final Random random = new Random();

    @GetMapping("/numbers")
    public int[] getRandomNumbers() {
        return random.ints(100).toArray();
    }

    @GetMapping("/number/{d}")
    public RandomNum getRandomNumber(@PathVariable int d) {
        return new RandomNum(random.nextInt((int) Math.pow(10, d)));
    }

    @PutMapping("/number")
    public ResponseEntity<RandomNum> updateRandomNumber(@RequestBody RandomNum randomNumber) {
        int newRandomNumber = random.nextInt((int) Math.pow(10, (int) Math.log10(randomNumber.getRandom()) + 1));
        return ResponseEntity.ok(new RandomNum(newRandomNumber));
    }
}