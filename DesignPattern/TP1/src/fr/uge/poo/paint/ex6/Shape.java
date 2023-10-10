package fr.uge.poo.paint.ex6;

import java.awt.*;

public interface Shape {
    void draw(Graphics2D graphics);
    double distance(int x, int y);
    WindowSize minWindowSize();

    static double distancePythagore(int x1, int y1, int x2, int y2){
        return (x2 - x1)*(x2 - x1) + (y2 -y1)*(y2 - y1);
    }
}