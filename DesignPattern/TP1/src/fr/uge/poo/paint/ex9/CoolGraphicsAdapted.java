package fr.uge.poo.paint.ex9;

import com.evilcorp.coolgraphics.CoolGraphics;
import com.evilcorp.coolgraphics.CoolGraphics.ColorPlus;

import java.awt.*;


public class CoolGraphicsAdapted implements Canva {

    private final CoolGraphics area;

    public CoolGraphicsAdapted(int width, int height){
        area = new CoolGraphics("area",width,height);
    }

    private ColorPlus colorToColorPlus(Color color){
        return switch (color){
            case Color.BLACK -> ColorPlus.BLACK;
            case Color.WHITE -> ColorPlus.WHITE;
            case Color.ORANGE -> ColorPlus.ORANGE;
            default -> throw new IllegalStateException("Unexpected value: " + color);
        };
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        area.drawLine(x1, y1, x2, y2,colorToColorPlus(color));
    }

    @Override
    public void drawRect(int x, int y, int width, int height, Color color) {
        var colorPlus = colorToColorPlus(color);
        area.drawLine(x,y,width + x,y,colorPlus);
        area.drawLine(x+width,y,x+width,y+height,colorPlus);
        area.drawLine(x,y,x,y+height,colorPlus);
        area.drawLine(x,y+height,x+width,y+height,colorPlus);
    }

    @Override
    public void drawOval(int x, int y, int width, int height, Color color) {
        area.drawEllipse(x,y,width,height,colorToColorPlus(color));
    }

    @Override
    public void clear() {
        area.repaint(ColorPlus.WHITE);

    }

    @Override
        public void waitForMouseEvents(Drawing drawing) {
        area.waitForMouseEvents((x,y) -> drawing.onClick(this,x,y));
    }
}
