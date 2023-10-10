package fr.uge.poo.paint.ex4_5;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class Drawing {
    private final ArrayList<Shape> allShapes;
    private Shape selected;

    public Drawing(ArrayList<Shape> allShapes){
        this.allShapes = allShapes;
    }

    public void drawAll(SimpleGraphics area) {
        area.clear(Color.WHITE);
        area.render(graphics -> {
            for(var shape : allShapes) {
                if (selected == shape) {
                    System.out.println(shape);
                    graphics.setColor(Color.ORANGE);
                } else {
                    graphics.setColor(Color.BLACK);
                }
                shape.draw(graphics);
            }
        });
    }

    public void onClick(SimpleGraphics area, int x, int y) {
        selected = allShapes.stream().min(Comparator.comparingDouble(s -> s.distance(x,y))).orElse(null);
        drawAll(area);
    }
}
