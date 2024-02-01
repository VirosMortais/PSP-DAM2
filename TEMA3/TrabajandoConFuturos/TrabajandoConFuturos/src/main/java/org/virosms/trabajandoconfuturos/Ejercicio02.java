package org.virosms.trabajandoconfuturos;

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.zip.*;

public class Ejercicio02 {

    public static void main(String[] args) {
        System.out.println("Ejercicio 02");

        String sourcePath = getPath("Enter the source path: ");
        String destinationPath = getPath("Enter the destination path: ");

        try {
            if(Files.isDirectory(Paths.get(sourcePath)))
                zipDirectory(sourcePath);
            else
                zipFile(new File(sourcePath), sourcePath, new ZipOutputStream(new FileOutputStream("temp.zip")));

            moveFile(destinationPath);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void zipDirectory(String sourceDirectory) throws IOException {
        FileOutputStream fos = new FileOutputStream("temp.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File dirToZip = new File(sourceDirectory);
        zipFile(dirToZip, dirToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            assert children != null;
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    private static void moveFile(String destinationPath) throws IOException {
        Path temp = Files.move(Paths.get("temp.zip"), Paths.get(destinationPath, "compressed.zip"));

        if(Files.exists(temp)) {
            System.out.println("File moved successfully");
        } else {
            System.out.println("Failed to move the file");
        }
    }

    private static String getPath(String prompt){
        Scanner in = new Scanner(System.in);
        System.out.print(prompt);
        return in.nextLine();
    }
}