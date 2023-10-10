package fr.uge.poo.paint.ex3;

import java.awt.*;

public record Rectangle(int x, int y, int width, int height) implements Shape{
    public void draw(Graphics2D graphics) {
        //graphics.setColor(Color.BLACK);
        graphics.drawRect(x, y, width, height);
    }
}
