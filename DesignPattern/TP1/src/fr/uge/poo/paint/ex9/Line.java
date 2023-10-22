package fr.uge.poo.paint.ex9;

import com.evilcorp.coolgraphics.CoolGraphics.*;

public record Line(int x1, int y1, int x2, int y2) implements Shape {
    @Override
    public void draw(Canva area, MyColor color) {
        area.drawLine(x1, y1, x2, y2,color);
    }
    @Override
    public double distance(int x, int y){
        int centerX = (x1 + x2)/2;
        int centerY = (y1 + y2)/2;
        return Shape.distancePythagore(centerX,centerY,x,y);
    }

    @Override
    public WindowSize minWindowSize() {
        return new WindowSize(Math.max(x1,x2), Math.max(y1,y2));
    }

}
