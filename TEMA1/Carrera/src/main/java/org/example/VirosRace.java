package org.example;

import org.example.race.Race;
import org.example.runners.Runner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VirosRace {
    public static void main(String[] args) {
        List<Runner> runnerList = new ArrayList<>();
        VirosRace.scanningRunners(runnerList);
        Race race = new Race(runnerList);
        race.start();
        try {
            race.end();
        }catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void scanningRunners(List<Runner> runnerList){
        Scanner sc = new Scanner(System.in);
        System.out.println("Number of runners: ");
        int runnerNumber = sc.nextInt();
        sc.nextLine();

        while (runnerList.size() < runnerNumber) {
            String runnerSymbol;
            int runnerSpeed, runnerTurbo, runnerFall;

            System.out.printf("Runner %d symbol: ", runnerList.size() + 1);
            runnerSymbol = sc.next();
            System.out.printf("Runner Attributes (Max 10 points)\r\nRunner %d base speed (1-5): ", runnerList.size() + 1);
            runnerSpeed = sc.nextInt();
            System.out.printf("Runner %d turbo probability (1-5): ", runnerList.size() + 1);
            runnerTurbo = sc.nextInt();
            System.out.printf("Runner %d fall probability (1-5): ", runnerList.size() + 1);
            runnerFall = sc.nextInt();

            try {
                runnerList.add(new Runner(runnerSymbol, runnerSpeed, runnerTurbo, runnerFall));
                System.out.printf("Runner %d created\r\n", runnerList.size());
            } catch (Exception e) {
                System.out.printf("Runner not created. Currently %d runners.\r\n", runnerList.size());
            }
        }

    }
}