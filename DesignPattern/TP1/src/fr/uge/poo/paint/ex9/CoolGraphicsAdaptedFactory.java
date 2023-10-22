package fr.uge.poo.paint.ex9;

public class CoolGraphicsAdaptedFactory implements CanvaFactory{

    @Override
    public Canva withData(int x, int y) {
        return new CoolGraphicsAdapted(x,y);
    }
}