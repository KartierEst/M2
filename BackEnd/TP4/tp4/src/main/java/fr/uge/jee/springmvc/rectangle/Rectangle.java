package fr.uge.jee.springmvc.rectangle;

import javax.validation.constraints.Min;

public class Rectangle {
    @Min(0)
    private int width;
    @Min(0)
    private int height;

    public int area(){return width*height;}

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

