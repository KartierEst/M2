package fr.uge.poo.paint.ex2;

import java.awt.*;

public record Line(int x1, int y1, int x2, int y2) {
    public void draw(Graphics2D graphics) {
        //sgraphics.setColor(Color.BLACK);
        graphics.drawLine(x1, y1, x2, y2);
    }
}
