package fr.uge.poo.paint.ex9;

import java.util.ArrayList;
import java.util.Comparator;
import com.evilcorp.coolgraphics.CoolGraphics;

public class Drawing {
    private final ArrayList<Shape> allShapes;
    private Shape selected;

    public Drawing(ArrayList<Shape> allShapes){
        this.allShapes = allShapes;
    }

    public void drawAll(Canva area) {
        area.clear();
        for(var shape : allShapes) {
            if (selected == shape) {
                shape.draw(area, MyColor.ORANGE);
            } else {
                shape.draw(area, MyColor.BLACK);
            }
        }
    }

    public WindowSize windowSize(){
        // reduce = commence par la valeur par défaut et appel la méthode compare petit à petit
        return allShapes.stream().map(Shape::minWindowSize).reduce(WindowSize.defaultWidth(), WindowSize::compareSize);
    }

    public void onClick(Canva area, int x, int y) {
        selected = allShapes.stream().min(Comparator.comparingDouble(s -> s.distance(x,y))).orElse(null);
        drawAll(area);
    }
}
