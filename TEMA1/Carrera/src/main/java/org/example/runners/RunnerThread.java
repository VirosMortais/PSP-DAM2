package org.example.runners;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunnerThread extends Thread{
    private Runner runner;
    private int raceLength;
    private TList raceResult;


    @Override
    public void run() {
        while (runner.goAllTheWay(raceLength)){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        raceResult.add(runner.getSymbol());
    }

}
