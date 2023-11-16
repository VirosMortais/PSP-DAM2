package org.example;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        Main main = new Main();

        String urlDescargar = "https://www.boe.es/legislacion/documentos/ConstitucionCASTELLANO.pdf";

        main.descargarArchivo(urlDescargar, "constitucion.txt");

        File archivo = new File("constitucion.txt");

        FileReader fr = new FileReader(archivo);

        BufferedReader br = new BufferedReader(fr);



        String linea;
        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }
    }

    public void descargarArchivo(String urlDescargar, String nombreArchivo) {
        System.out.println("Descargando archivo: " + nombreArchivo + " de la url: " + urlDescargar);

        try{
            // URL laUrl = new URL("https://www.google.com");
            URL laUrl = new URL(urlDescargar);

            // input stream
            InputStream is = laUrl.openStream();

            InputStreamReader reader = new InputStreamReader(is);

            BufferedReader br = new BufferedReader(reader);

            FileWriter fw = new FileWriter(nombreArchivo);

            String linea;

            while((linea = br.readLine()) != null) {
                fw.write(linea);
            }

            fw.close();
            br.close();
            reader.close();
            is.close();

        } catch (MalformedURLException e) {
            System.out.println("La url no es valida: " + urlDescargar);
        } catch (IOException e) {
            System.out.println("Error al descargar el archivo: " + nombreArchivo);
        }
    }
}