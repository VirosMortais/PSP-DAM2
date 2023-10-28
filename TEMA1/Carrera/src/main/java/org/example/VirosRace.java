package org.example;

import org.example.race.Race;
import org.example.runners.Runner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class of the program
 * will ask for the number of runners and the attributes of each runner
 *
 *
 * @author Charles Arruda Santos
 * @version 1.0.0
 * @since 2023-10-28
 * @see org.example.runners.Runner
 * @see org.example.race.Race
 *
 * */
public class VirosRace {
    public static void main(String[] args) {
        //List of runners
        List<Runner> runnerList = new ArrayList<>();
        //Method to scan the number of runners and their attributes
        VirosRace.scanningRunners(runnerList);
        //Create a race with the runners
        Race race = new Race(runnerList);

        //Start the race
        race.start();

        try {
            //Race end
            race.end();
        }catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to scan the number of runners and their attributes
     * @param runnerList List of runners
     * */
    public static void scanningRunners(List<Runner> runnerList){
        //Scanner to scan the number of runners and their attributes
        Scanner sc = new Scanner(System.in);
        //Number of runners
        System.out.println("Number of runners: ");
        int runnerNumber = sc.nextInt();
        //Clear the scanner
        sc.nextLine();

        //While the number of runners is less than the number of runners
        while (runnerList.size() < runnerNumber) {
            //Symbol of the runner
            String runnerSymbol;
            //Attributes of the runner
            int runnerSpeed, runnerTurbo, runnerFall;

            //Read the symbol
            System.out.printf("Runner %d symbol: ", runnerList.size() + 1);
            runnerSymbol = sc.next();

            //Read the base speed
            System.out.printf("Runner Attributes (Max 10 points)\r\nRunner %d base speed (1-5): ", runnerList.size() + 1);
            runnerSpeed = sc.nextInt();

            //Read the turbo probability
            System.out.printf("Runner %d turbo probability (1-5): ", runnerList.size() + 1);
            runnerTurbo = sc.nextInt();

            //Read the fall probability
            System.out.printf("Runner %d fall probability (1-5): ", runnerList.size() + 1);
            runnerFall = sc.nextInt();

            try {
                //Add the runner to the list
                runnerList.add(new Runner(runnerSymbol, runnerSpeed, runnerTurbo, runnerFall));
                System.out.printf("Runner %d created\r\n", runnerList.size());
            } catch (Exception e) {
                System.out.printf("Runner not created. Currently %d runners.\r\n", runnerList.size());
            }
        }

    }
}