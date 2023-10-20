package org.example;

import lombok.Data;

@Data
public class Contador {
    int count = 0;

    public void subirContador() {
        count++;
    }

}
