package org.example;


import lombok.Data;

import java.io.*;

/**
 * The Command class represents a command to be executed in a shell environment.
 * It allows you to specify a command with arguments and an optional output redirection path.
 * The class provides methods for executing the command, capturing its output, and handling errors.
 *
 * @author Charles Arruda Santos
 * @version 1.0
 */
@Data
public class Command {
    /**
     * The attributes of the Command class are:
     */
    private String args;

    private String redirectPath;
    private int pid = -1;
    private String output = null;
    private int exitValue = -1;
    private Process ps;

    /**
     * The constructor of the Command class receives a String with the command and its arguments.
     * If the command includes a redirection, the constructor receives the redirection path as a second argument.
     *
     * @param args         The command and its arguments.
     * @param redirectPath The redirection path.
     */
    public Command(String[] args, String redirectPath) {
        this.args = String.join(" ", args);
        this.redirectPath = redirectPath;
    }

    /**
     * The constructor of the Command class receives a String with the command and its arguments.
     * The constructor parses the command and its arguments and stores them in the args attribute.
     *
     *
     * @param args The command and its arguments.
     */
    public Command(String args) {
        if (args.contains(">")) {
            String[] parts = args.split(">");
            this.args = parts[0].trim();
            this.redirectPath = parts[1].trim();
        } else {
            this.args = args;
            this.redirectPath = "";
        }
    }

    /**
     * The executeCommand method executes the command and captures its output.
     * If the command includes a redirection, the output is written to the specified file.
     * The method also captures the exit value of the command.
     * If the command is executed successfully, the exit value is 0.
     * Otherwise, the exit value is a non-zero integer.
     * The method capture two types of exceptions: IOException and InterruptedException.
     *
     * @return The output of the command.
     */
    public String executeCommand() {
        try {
            ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", args);
            pb.redirectErrorStream(true);

            Process process = pb.start();
            pid = (int) process.pid();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                output = reader.lines().reduce("", (line1, line2) -> line1 + "\n" + line2);
            }

            exitValue = process.waitFor();

            if (!redirectPath.isEmpty()) {
                try (FileWriter fw = new FileWriter(redirectPath)) {
                    fw.write(output);
                }
            }

            return output;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * The toString method returns a String representation of the Command object.
     *
     * @return A String representation of the Command object.
     */
    @Override
    public String toString() {
        String result = "Command: " + args + "\n";
        result += "Number of Parameters: " + args.split(" ").length + "\n";
        result += "Parameters: " + args + "\n";
        if (pid != -1) {
            result += "PID (Process ID): " + pid + "\n";
        }
        if (output != null) {
            result += "Output:\n" + output + "\n";
        }
        if (exitValue != -1) {
            result += "Exit Value: " + exitValue + "\n";
            result += "Command " + (exitValue == 0 ? "completed" : "failed") + "\n";
        }
        return result;
    }

    /**
     * redirectPath is the getter for the rediretPath attribute.
     *
     * @return redirectPath if it is not null, otherwise an empty String.
     */
    public String getRedirectPath() {
        return redirectPath == null ? "" : redirectPath;
    }
}




