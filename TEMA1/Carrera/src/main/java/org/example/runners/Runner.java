package org.example.runners;

import lombok.Data;

/**
 * Runner class
 * the runner has a symbol, a turbo, a base speed and a fall chance
 * the runner can go all the way and fall all the way
 * the runner can be represented by its symbol
 *
 * @author Charles Arruda Santos
 * @version 1.0.0
 * @since 2023-10-28
 * @see RunnerThread
 * @see RunnerFallThread
 */
@Data
public class Runner {

    private String symbol;
    private int speed;
    private int turbo;
    private int fallChance;
    private int position;

    /**
     * Constructor of the runner
     * we need the symbol, the turbo, the base speed and the fall chance
     * we validate the attributes of the runner to be less than or equal to 5 and greater than 0
     * and the sum of the attributes must be less than or equal to 10
     *
     * @param symbol is the symbol of the runner
     * @param turbo is the turbo of the runner
     * @param speed is the base speed of the runner
     * @param fallChance is the fall chance of the runner
     */
    public Runner(String symbol, int turbo, int speed, int fallChance) {
        this.symbol = symbol;
        this.position = 0;

        //Validate the attributes of the runner
        if (!validateFields(speed, turbo, fallChance)) {
            throw new IllegalArgumentException("Attributes must be less than or equal to 5 and greater than 0");
        }

        this.turbo = turbo;
        this.speed = speed;
        this.fallChance = fallChance;

    }

    /**
     * Method to validate the attributes of the runner
     * the attributes must be less than or equal to 5 and greater than 0
     * @param baseSpeed is the base speed of the runner
     * @param turbo is the turbo of the runner
     * @param fallChance is the fall chance of the runner
     * @return true if the attributes are valid, false if not
     */
    private boolean validateFields(int baseSpeed, int turbo, int fallChance) {
        //The sum of the attributes must be less than or equal to 10
        int sum = baseSpeed + turbo + fallChance;
        //Return true if the attributes are valid, false if not
        return baseSpeed <= 5
                && turbo <= 5
                && fallChance <= 5
                && baseSpeed > 0
                && turbo > 0
                && fallChance > 0
                && sum <= 10;

    }

    /**
     * Method to go all the way
     * we check if the runner can go all the way with the turbo probability
     * and increase the position of the runner one or two times with the base speed depending on the turbo probability
     *
     * @param raceEnd is the race length to go all the way
     * @return true if the runner can go all the way, false if not
     */
    public synchronized boolean goAllTheWay(int raceEnd){
        //check if the runner is finished
        if(this.position < raceEnd){
            //we check if the runner can go all the way with the turbo probability
            if(Math.random() < turbo / 10.){
                //if the runner can go all the way with the turbo probability, we add the turbo to the base speed
                this.position += speed;
            }
            //if the runner can't go all the way with the turbo probability, we add the base speed
            this.position += speed;
        }
        //return true if the runner can go all the way, false if not
        return this.position < raceEnd;
    }


    /**
     * Method to fall all the way
     * we check if the runner can fall all the way with the fall chance
     * and decrease the position of the runner with the base speed if the runner can fall all the way
     *
     * @param raceEnd is the race length to fall all the way
     * @return true if the runner can fall all the way, false if not
     */
    public synchronized boolean fallAllTheWay(int raceEnd){
        //check if the runner is finished
        if (position < raceEnd){
            //we check if the runner can fall all the way with the fall chance
            if (Math.random() < fallChance / 10.){
                //if the runner can fall all the way with the fall chance, we subtract the base speed
                position -= speed;
            }
        }
        //return true if the runner can fall all the way, false if not
        return position < raceEnd;
    }

    /**
     * Method to represent the runner by its symbol
     * @return the symbol of the runner
     */
    @Override
    public String toString() {
        return getSymbol();
    }
}
