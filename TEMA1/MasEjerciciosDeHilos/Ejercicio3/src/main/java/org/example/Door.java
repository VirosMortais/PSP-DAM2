package org.example;

public class Door {
    private boolean ocupaied;

    public Door() {
        ocupaied = false;
    }

    public boolean isOcupaied() {
        return ocupaied;
    }

    public synchronized boolean releaseDoor(){
        return ocupaied = false;
    }

    public synchronized boolean occupyDoor(){
        if(ocupaied) return false;

        return ocupaied = true;
    }
}
