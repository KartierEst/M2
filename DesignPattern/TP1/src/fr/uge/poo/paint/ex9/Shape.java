package fr.uge.poo.paint.ex9;

import com.evilcorp.coolgraphics.CoolGraphics.*;

public interface Shape {
    void draw(Canva area, MyColor color);
    double distance(int x, int y);
    WindowSize minWindowSize();
    static double distancePythagore(int x1, int y1, int x2, int y2){
        return (x2 - x1)*(x2 - x1) + (y2 -y1)*(y2 - y1);
    }
}