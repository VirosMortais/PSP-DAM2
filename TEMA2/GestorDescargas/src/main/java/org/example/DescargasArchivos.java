//package org.example;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.nio.channels.FileChannel;
//import java.nio.file.Path;
//import java.nio.file.StandardOpenOption;
//
//public class Main {
//    public static void main(String[] args) throws IOException {
//        Main main = new Main();
//
//        String urlDescargar = "https://www.boe.es/legislacion/documentos/ConstitucionCASTELLANO.pdf";
//        String nombreArchivo = "constitucion.txt";
//
//        main.descargarArchivo(urlDescargar, nombreArchivo);
//
//        Path archivoPath = Path.of(nombreArchivo);
//        FileChannel archivo = FileChannel.open(archivoPath, StandardOpenOption.READ);
//
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(archivo))) {
//            String linea;
//            while ((linea = br.readLine()) != null) {
//                System.out.println(linea);
//            }
//        }
//    }
//
//    public void descargarArchivo(String urlDescargar, String nombreArchivo) {
//        System.out.println("Descargando archivo: " + nombreArchivo + " de la url: " + urlDescargar);
//
//        try {
//            URL laUrl = new URL(urlDescargar);
//            Path destino = Path.of(nombreArchivo);
//
//            FileChannel fileChannel = FileChannel.open(destino, StandardOpenOption.WRITE);
//
//            try (ReadableByteChannel readableByteChannel = Channels.newChannel(laUrl.openStream())) {
//                fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
//            }
//        } catch (MalformedURLException e) {
//            System.out.println("La url no es valida: " + urlDescargar);
//        } catch (IOException e) {
//            System.out.println("Error al descargar el archivo: " + nombreArchivo);
//        }
//    }
//}


