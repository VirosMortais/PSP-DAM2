package org.example.runners;

import lombok.Data;

@Data
public class Runner {

    private String symbol;
    private int turbo;
    private int baseSpeed;
    private int fallChance;
    private int position;

    public Runner(String symbol, int turbo, int baseSpeed, int fallChance) {
        this.symbol = symbol;
        this.position = 0;

        if (!validateAttributes(baseSpeed, turbo, fallChance)) {
            throw new IllegalArgumentException("Attributes must be less than or equal to 5 and greater than 0");
        }

        this.turbo = turbo;
        this.baseSpeed = baseSpeed;
        this.fallChance = fallChance;

    }


    private boolean validateAttributes(int baseSpeed, int turbo, int fallChance) {
        int sum = baseSpeed + turbo + fallChance;
        return baseSpeed <= 5
                && turbo <= 5
                && fallChance <= 5
                && baseSpeed > 0
                && turbo > 0
                && fallChance > 0
                && sum <= 10;

    }

    public synchronized boolean goAllTheWay(int raceEnd){
        if(this.position < raceEnd){
            if(Math.random() < turbo / 10.){
                this.position += baseSpeed;
            }
            this.position += baseSpeed;
        }
        return this.position < raceEnd;
    }

    public synchronized boolean fallAllTheWay(int raceEnd){
        if (position < raceEnd){
            if (Math.random() < fallChance / 10.){
                position -= baseSpeed;
            }
        }
        return position < raceEnd;
    }

    @Override
    public String toString() {
        return getSymbol();
    }
}
