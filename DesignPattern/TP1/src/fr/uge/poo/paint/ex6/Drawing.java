package fr.uge.poo.paint.ex6;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

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
                    graphics.setColor(Color.ORANGE);
                } else {
                    graphics.setColor(Color.BLACK);
                }
                shape.draw(graphics);
            }
        });
    }

    public WindowSize windowSize(){
        // reduce = commence par la valeur par défaut et appel la méthode compare petit à petit
        return allShapes.stream().map(Shape::minWindowSize).reduce(WindowSize.defaultWidth(),WindowSize::compareSize);
    }

    public void onClick(SimpleGraphics area, int x, int y) {
        // pas bon selected
        selected = allShapes.stream().min(Comparator.comparingDouble(s -> s.distance(x,y))).orElse(null);
        System.out.println("click " + x + " " + y);
        System.out.println("selected " + selected);
        drawAll(area);
    }
}
