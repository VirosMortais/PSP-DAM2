package org.example.race;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.runners.TList;

/**
 * This class we use to
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RUIT extends Thread{
    private Race race;
    private TList raceResults;
    private int runners;

    @Override
    public void run(){
        while (raceResults.size() < runners){
            try {
                System.out.printf(race.toString());
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
