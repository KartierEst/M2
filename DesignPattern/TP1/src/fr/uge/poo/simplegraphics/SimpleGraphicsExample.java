package fr.uge.poo.simplegraphics;



import java.awt.Color;
import java.awt.Graphics2D;

public class SimpleGraphicsExample {
    private static void drawAll(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(100, 20, 40, 140);
        graphics.drawLine(100,20,140,160);
        graphics.drawLine(100,160,140,20);
        graphics.drawOval(100,20,40,140);
    }

    private static void drawDiag(Graphics2D graphics, int width, int height) {
        graphics.setColor(Color.BLACK);
        //graphics.drawLine(0, 0, width, height);
        //graphics.drawLine(0, height, width, 0);
    }

    public static void main(String[] args) {
        var width = 800;
        var height = 600;
        SimpleGraphics area = new SimpleGraphics("area", width, height);
        area.clear(Color.WHITE);
        area.render(SimpleGraphicsExample::drawAll);
        //area.render(graphics -> drawDiag(graphics, width, height));
        //canvas.render(graphics -> drawAll(graphics));
    }
}
