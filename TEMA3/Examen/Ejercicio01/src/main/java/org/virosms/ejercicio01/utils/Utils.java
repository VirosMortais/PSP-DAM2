package org.virosms.ejercicio01.utils;

import java.util.UUID;

/**
 * The Utils class provides utility methods for the application.
 */
public class Utils {

    /**
     * The genereteRandomString method generates a random string of 20 characters.
     * It uses the UUID class to generate a random UUID and then takes the first 20 characters of the UUID string.
     *
     * @return A random string of 20 characters.
     */
    public static String genereteRandomString(){
        return UUID.randomUUID().toString().substring(0, 20);
    }
}