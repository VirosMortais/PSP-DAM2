package org.example.runners;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class is a thread that makes a runner run
 * we use this thread to make the runner run all the way
 * we only stop the thread when the runner has run all the way
 * the class got 3 attributes:
 * - runner: the runner that is going to run all the way while running
 * - raceLength: the length of race, we use this to know when the runner has run all the way
 * - raceResult: the list of runners
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunnerThread extends Thread{
    private Runner runner;
    private int raceLength;
    private TList raceResult;


    /**
     * This method is the one that makes the runner run
     * we use this method to make the runner run while the runner is running
     * we only stop the thread when the runner has run all the way
     *
     * @see Runner#goAllTheWay(int)
     * @see TList#add(String)
     */
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
