package org.example.runners;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TList {
    private List<String> list;

    public TList() {
        list = new ArrayList<>();
    }

    public synchronized void add(String symbol){
        list.add(symbol);
    }

    public int size() {
        return list.size();
    }

    public String get(int i) {
        return list.get(i);
    }
}
