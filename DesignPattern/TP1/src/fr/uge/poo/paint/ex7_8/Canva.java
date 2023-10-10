package fr.uge.poo.paint.ex7_8;

import com.evilcorp.coolgraphics.CoolGraphics;
import com.evilcorp.coolgraphics.CoolGraphics.*;
import fr.uge.poo.simplegraphics.SimpleGraphics;
import fr.uge.poo.simplegraphics.SimpleGraphics.MouseCallback;
import org.w3c.dom.events.MouseEvent;

import java.awt.*;
import java.awt.event.MouseListener;

public interface Canva {
    void drawLine(int x1, int y1, int x2, int y2,ColorPlus color);
    void drawRect(int x,int y,int width,int height,ColorPlus color);
    void drawOval(int x,int y,int width,int height,ColorPlus color);
    void clear();
    void waitForMouseEvents(Drawing drawing);
}
