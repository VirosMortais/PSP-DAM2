package org.virosms.ejercicio02;

/**
 * The UrlEntry record represents a URL entry in the list.
 * It contains a URL and a random string.
 * This is a record, a special kind of class in Java that is used to model immutable data.
 * Each instance of a record gets a fixed set of fields, each with its own type.
 * Records also get a whole host of automatically implemented methods, including equals, hashCode, and toString.
 */
public record UrlEntry(String url, String randomString) {
}