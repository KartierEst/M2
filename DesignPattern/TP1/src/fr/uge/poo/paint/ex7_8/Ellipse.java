package fr.uge.poo.paint.ex7_8;

import com.evilcorp.coolgraphics.CoolGraphics;

import java.awt.*;

public class Ellipse extends RectEll {

    public Ellipse(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Canva area, CoolGraphics.ColorPlus color) {
        area.drawOval(x, y, width, height,color);
    }

}
