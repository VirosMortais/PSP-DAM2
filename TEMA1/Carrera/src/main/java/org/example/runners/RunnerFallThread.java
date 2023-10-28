package org.example.runners;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RunnerFallThread extends Thread{
    private Runner runner;
    private int raceLength;

    @Override
    public void run() {
        while (runner.fallAllTheWay(raceLength)){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

}
