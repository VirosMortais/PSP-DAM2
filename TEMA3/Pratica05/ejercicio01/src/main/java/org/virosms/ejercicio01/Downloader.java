package org.virosms.ejercicio01;

import javafx.collections.ListChangeListener;

public class Downloader implements ListChangeListener<String> {

    @Override
    public void onChanged(Change<? extends String> change) {
        System.out.println("Se ha iniciado la descarga del archivo URL" + change.getList().getLast());
        System.out.println("Descargando archivo...\n\n\n");
    }
}
