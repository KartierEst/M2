package fr.uge.poo.paint.ex3;

import java.awt.*;

public record Line(int x1, int y1, int x2, int y2) implements Shape{
    public void draw(Graphics2D graphics) {
        //graphics.setColor(Color.BLACK);
        graphics.drawLine(x1, y1, x2, y2);
    }
}
