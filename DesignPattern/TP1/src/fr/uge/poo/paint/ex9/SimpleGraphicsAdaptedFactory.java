package fr.uge.poo.paint.ex9;

public class SimpleGraphicsAdaptedFactory implements CanvaFactory{

    @Override
    public Canva withData(int x, int y) {
        return new SimpleGraphicsAdapted(x,y);
    }
}
