package fr.uge.poo.paint.ex9;

public record WindowSize(int width, int height) {
    public static final int MIN_WIDTH = 500;
    public static final int MIN_HEIGHT = 500;
    public WindowSize {
        if(width < 0){
            throw new IllegalArgumentException("the width is negative or null");
        }
        if(height < 0){
            throw new IllegalArgumentException("the height is negative or null");
        }
    }

    public WindowSize compareSize(WindowSize windowSize){
        return new WindowSize(Math.max(width, windowSize.width), Math.max(height, windowSize.height));
    }

    public static WindowSize defaultWidth(){
        return new WindowSize(MIN_WIDTH,MIN_HEIGHT);
    }
}
