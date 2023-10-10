package fr.uge.poo.paint.ex7_8;

import com.evilcorp.coolgraphics.CoolGraphics.*;
import com.evilcorp.coolgraphics.CoolGraphics;

import java.awt.*;


public class CoolGraphicsAdapted implements Canva {

    private final CoolGraphics area;

    public CoolGraphicsAdapted(int width, int height){
        area = new CoolGraphics("area",width,height);
    }
    
    @Override
    public void drawLine(int x1, int y1, int x2, int y2, ColorPlus color) {
        area.drawLine(x1, y1, x2, y2,color);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, ColorPlus color) {
        area.drawLine(x,y,width + x,y,color);
        area.drawLine(x+width,y,x+width,y+height,color);
        area.drawLine(x,y,x,y+height,color);
        area.drawLine(x,y+height,x+width,y+height,color);
    }

    @Override
    public void drawOval(int x, int y, int width, int height, ColorPlus color) {
        System.out.println(color.toString());
        area.drawEllipse(x,y,width,height,color);
    }

    @Override
    public void clear() {
        area.repaint(CoolGraphics.ColorPlus.WHITE);

    }

    @Override
        public void waitForMouseEvents(Drawing drawing) {
        area.waitForMouseEvents((x,y) -> drawing.onClick(this,x,y));
    }
}
