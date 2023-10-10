package fr.uge.poo.paint.ex7_8;

import com.evilcorp.coolgraphics.CoolGraphics;
import com.evilcorp.coolgraphics.CoolGraphics.ColorPlus;
import fr.uge.poo.simplegraphics.SimpleGraphics;
import fr.uge.poo.simplegraphics.SimpleGraphics.MouseCallback;
import org.w3c.dom.events.MouseEvent;


import java.awt.*;
import java.util.function.BiConsumer;

public class SimpleGraphicsAdapted implements Canva {

    private final SimpleGraphics area;

    public SimpleGraphicsAdapted(int width, int height){
        area = new SimpleGraphics("area",width,height);
    }

    private Color colorPlusToColor(ColorPlus color){
        return switch (color){
            case BLACK -> Color.BLACK;
            case WHITE -> Color.WHITE;
            case ORANGE -> Color.ORANGE;
            default -> throw new IllegalStateException("Unexpected value: " + color.name());
        };
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2,ColorPlus color) {
        area.render(graphics2D -> {
            graphics2D.setColor(colorPlusToColor(color));
            graphics2D.drawLine(x1,y1,x2,y2);
        });
    }

    @Override
    public void drawRect(int x, int y, int width, int height,ColorPlus color) {
        area.render(graphics2D -> {
            graphics2D.setColor(colorPlusToColor(color));
            graphics2D.drawRect(x, y, width, height);
        });
    }

    @Override
    public void drawOval(int x, int y, int width, int height,ColorPlus color) {
        area.render(graphics2D -> {
            graphics2D.setColor(colorPlusToColor(color));
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
