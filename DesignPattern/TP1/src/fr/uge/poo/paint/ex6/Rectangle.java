package fr.uge.poo.paint.ex6;

import java.awt.*;

public class Rectangle extends RectEll { // pas de record pour extends abstract class

    public Rectangle(int x, int y, int width, int height){
        super(x,y,width,height);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawRect(x, y, width, height);
    }
}
