package org.example;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Contador {
    private AtomicInteger valor;
    public synchronized void incremental() {
        if(valor == null)
            valor = new AtomicInteger();
        valor.incrementAndGet();
    }
}
