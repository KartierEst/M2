package fr.uge.poo.paint.ex9;

import com.evilcorp.coolgraphics.CoolGraphics.*;

public interface Canva {
    void drawLine(int x1, int y1, int x2, int y2,ColorPlus color);
    void drawRect(int x,int y,int width,int height,ColorPlus color);
    void drawOval(int x,int y,int width,int height,ColorPlus color);
    void clear();
    void waitForMouseEvents(Drawing drawing);
}
