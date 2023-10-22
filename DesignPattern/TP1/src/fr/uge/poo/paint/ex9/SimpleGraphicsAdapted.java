package fr.uge.poo.paint.ex9;

import com.evilcorp.coolgraphics.CoolGraphics.ColorPlus;
import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;

public class SimpleGraphicsAdapted implements Canva {

    private final SimpleGraphics area;

    private Color colorToColorPlus(MyColor color){
        return switch (color){
            case BLACK -> Color.BLACK;
            case WHITE -> Color.WHITE;
            case ORANGE -> Color.ORANGE;
            default -> throw new IllegalStateException("Unexpected value: " + color);
        };
    }

    public SimpleGraphicsAdapted(int width, int height){
        area = new SimpleGraphics("area",width,height);

    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2,MyColor color) {
        area.render(graphics2D -> {
            graphics2D.setColor(colorToColorPlus(color));
            graphics2D.drawLine(x1,y1,x2,y2);
        });
    }

    @Override
    public void drawRect(int x, int y, int width, int height,MyColor color) {
        area.render(graphics2D -> {
            graphics2D.setColor(colorToColorPlus(color));
            graphics2D.drawRect(x, y, width, height);
        });
    }

    @Override
    public void drawOval(int x, int y, int width, int height,MyColor color) {
        area.render(graphics2D -> {
            graphics2D.setColor(colorToColorPlus(color));
            graphics2D.drawOval(x, y, width, height);
        });
    }

    @Override
    public void waitForMouseEvents(Drawing drawing) {
        area.waitForMouseEvents((x,y) -> drawing.onClick(this,x,y));
    }

    @Override
    public void clear() {
        area.clear(Color.WHITE);
    }
}
