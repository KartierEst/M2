package fr.uge.poo.paint.ex9;

import com.evilcorp.coolgraphics.CoolGraphics;

public class Ellipse extends RectEll {

    public Ellipse(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Canva area, MyColor color) {
        area.drawOval(x, y, width, height,color);
    }

}
