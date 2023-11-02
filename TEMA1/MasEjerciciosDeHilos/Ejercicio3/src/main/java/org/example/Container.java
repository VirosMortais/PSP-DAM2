package org.example;

public class Container {
    private int capacity;

    public Container(int capacity) {
        this.capacity = capacity;
    }

    public boolean getProduct() {
        if (capacity > 0) {
            capacity--;
            return true;
        }
        return false;
    }
}
