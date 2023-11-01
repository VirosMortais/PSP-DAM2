package org.example.race;

import lombok.Data;
import org.example.runners.Runner;
import org.example.runners.RunnerFallThread;
import org.example.runners.RunnerThread;
import org.example.runners.TList;

import java.util.ArrayList;
import java.util.List;

@Data
public class Race {
    private List<Runner> runnersList;
    private List<RunnerThread> runnerThreadList;
    private  List<RunnerFallThread> fallThreadList;

    private TList raceResults;

    private RUIT raceUI;
    private static final int RACE_END = 100;

    public Race (List<Runner> runnersList){
        this.runnersList = runnersList;
        this.runnerThreadList = new ArrayList<>();
        this.fallThreadList = new ArrayList<>();
        this.raceResults = new TList();

        for (Runner runner : runnersList){
            this.runnerThreadList.add(new RunnerThread(runner, Race.RACE_END, raceResults));
            this.fallThreadList.add(new RunnerFallThread(runner, Race.RACE_END));
        }

        this.raceUI = new RUIT(this, raceResults, runnersList.size());
    }


    public void start(){
        for (RunnerThread runnerThread : runnerThreadList){
            runnerThread.start();
        }
        for (RunnerFallThread fallThread : fallThreadList){
            fallThread.start();
        }
        raceUI.start();
    }

    public void end() throws InterruptedException {
        for (RunnerThread runnerThread : runnerThreadList){
            runnerThread.join();
        }
        for (RunnerFallThread fallThread : fallThreadList){
            fallThread.join();
        }
        raceUI.join();

        printClassication();
    }

    private void printClassication() {
        System.out.print("Classification:\r\n");
        for (int i = 0; i < raceResults.size(); i++) {
            System.out.printf("%d - %s\r\n", i+1, raceResults.get(i));
        }
    }


    @Override
    public String toString(){
        StringBuilder ui = new StringBuilder("\r\n".repeat(10) + "=".repeat(102) + "\r\n");
        for (Runner runner: runnersList) {
            ui.append("¡").append(" ".repeat(runner.getPosition())).append(runner.getSymbol()).append(" "
                    .repeat(Math.max(RACE_END - runner.getPosition() - 1, 0))).append("!\r\n");
        }
        ui.append("=".repeat(102)).append("\r\n");
        return ui.toString();
    }
}
