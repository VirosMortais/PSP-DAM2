package org.example.runners;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class is a thread that makes a runner fall
 * we use this thread to make the runner fall while the runner is running
 * we only stop the thread when the runner has fallen all the way
 * the class got 2 attributes:
 * - runner: the runner that is going to fall all the way while running
 * - raceLength: is the length of the race, we use this to know when the runner has fallen all the way
 *
 * @see RunnerFallThread#run()
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RunnerFallThread extends Thread{
    private Runner runner;
    private int raceLength;

    /**
     * This method is the one that makes the runner fall
     * we use this method to make the runner fall while the runner is running
     * we only stop the thread when the runner has fallen all the way
     * @see Runner#fallAllTheWay(int)
     *
     */
    @Override
    public void run() {
        // while the runner has not fallen all the way
        while (runner.fallAllTheWay(raceLength)){
            try {
                // we wait 1 second
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

}
