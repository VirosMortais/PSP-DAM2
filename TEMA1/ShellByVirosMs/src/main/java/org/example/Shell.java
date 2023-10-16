package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Shell {


    /**
     * The main method implements a simple shell that allows you to execute commands.
     * It also allows you to redirect the output of a command to a file.
     * The shell keeps track of the last command executed.
     * It also allows you to retrieve information about the last command executed.
     * The shell is implemented as a loop that reads commands from the standard input.
     * The loop exits when the user enters the command "exit".
     * The shell us implemented using the Command class.
     *
     * @param args The command line arguments.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Command lastCommand = null;

        System.out.println("Bienvenido al Shell Simple");
        while (true) {
            System.out.print(">> ");
            String input = reader.readLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Saliendo del Shell.");
                break;
            } else if (input.equalsIgnoreCase("last-command")) {
                if (lastCommand != null) {
                    System.out.println("Información del último comando:");
                    System.out.println(lastCommand);
                } else {
                    System.out.println("No se ha ejecutado ningún comando anteriormente.");
                }
            } else {
                Command cmd = new Command(input);

                String output = cmd.executeCommand();
                if (output.isEmpty()) {
                    System.out.println("Salida está redireccionada al archivo: " + cmd.getRedirectPath());
                } else {
                    System.out.println("Resultado del comando:");
                    System.out.println(output);
                }

                lastCommand = cmd;
            }
        }
    }

}