package org.virosms.ejercicio03.entity;

import lombok.Data;

@Data
public class RandomNum {
    private int random;

    public RandomNum() {}

    public RandomNum(int random) {
        this.random = random;
    }
    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }
}
