package fr.uge.jee.springmvc.rectanglesession;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringJoiner;

public class History {
    private final ArrayDeque<String> rectangles = new ArrayDeque<>();

    public void add(String string){
        rectangles.add(string);
    }

    public ArrayDeque<String> getList() {
        return rectangles;
    }
}
