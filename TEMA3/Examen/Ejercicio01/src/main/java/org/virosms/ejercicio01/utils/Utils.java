package org.virosms.ejercicio01.utils;

import java.util.UUID;

public class Utils {

    public static String genereteRandomString(){
        return UUID.randomUUID().toString().substring(0, 20);
    }
}
