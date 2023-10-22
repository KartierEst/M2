package fr.uge.poo.paint.ex9;

import com.evilcorp.coolgraphics.CoolGraphics.*;

public class Rectangle extends RectEll { // pas de record pour extends abstract class

    public Rectangle(int x, int y, int width, int height){
        super(x,y,width,height);
    }

    @Override
    public void draw(Canva area, MyColor color) {
        area.drawRect(x, y, width, height,color);
    }
}
