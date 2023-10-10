package fr.uge.poo.paint.ex4_5;

public abstract class RectEll implements Shape  {
    int x;
    int y;
    int width;
    int height;

    public RectEll(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public double distance(int x, int y){
        int centerX = this.x + (width/2);
        int centerY = this.y + (height/2);
        return Shape.distancePythagore(centerX,centerY,x,y);
    }

}
