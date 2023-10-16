package org.example;


import lombok.Data;

import java.io.*;


@Data
public class Command {

    private String args;
    private String redirectPath;
    private int pid = -1;
    private String output = null;
    private int exitValue = -1;
    private Process ps;

    public Command(String[] args, String redirectPath) {
        this.args = String.join(" ", args);
        this.redirectPath = redirectPath;
    }

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

    public String executeCommand() {
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", args);
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

    public String getRedirectPath() {
        return redirectPath + " (PID: " + pid + ")";
    }
}




