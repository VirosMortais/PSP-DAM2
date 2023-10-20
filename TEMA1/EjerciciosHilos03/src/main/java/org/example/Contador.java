package org.example;

import lombok.Data;

@Data
public class Contador {
    private int valor;
    public  void incrementar() {
        this.valor++;
    }
}
