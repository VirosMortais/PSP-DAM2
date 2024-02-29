package org.virosms.ejercicio01;

public class UrlEntry {
    private String url;
    private String randomString;

    public UrlEntry(String url, String randomString){
        this.url = url;
        this.randomString = randomString;
    }

    public String getUrl(){
        return url;
    }

    public String getRandomString(){
        return randomString;
    }
}
