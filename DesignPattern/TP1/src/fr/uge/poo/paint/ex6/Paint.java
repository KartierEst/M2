package fr.uge.poo.paint.ex6;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Paint {

    int width;
    int height;

    public Paint(int width, int height){
        this.width = width;
        this.height = height;
    }

    public Paint(){
        this.width = 500;
        this.height = 500;
    }

    public static ArrayList<Shape> readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        ArrayList<Shape> shapes = new ArrayList<>();
        try(Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                String[] tokens = line.split(" ");
                shapes.add(createShape(tokens));
            });
        }
        return shapes;
    }

    public static Shape createShape(String[] tokens){
        switch (tokens[0]) {
            case "line" -> {
                int x1 = Integer.parseInt(tokens[1]);
                int y1 = Integer.parseInt(tokens[2]);
                int x2 = Integer.parseInt(tokens[3]);
                int y2 = Integer.parseInt(tokens[4]);
                return new Line(x1,y1,x2,y2);
            }
            case "rectangle" -> {
                int x1 = Integer.parseInt(tokens[1]);
                int y1 = Integer.parseInt(tokens[2]);
                int width = Integer.parseInt(tokens[3]);
                int height = Integer.parseInt(tokens[4]);
                return new Rectangle(x1, y1, width, height);
            }
            case "ellipse" -> {
                int x1 = Integer.parseInt(tokens[1]);
                int y1 = Integer.parseInt(tokens[2]);
                int width = Integer.parseInt(tokens[3]);
                int height = Integer.parseInt(tokens[4]);
                return new Ellipse(x1, y1, width, height);
            }
            default -> throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) throws IOException {
        var shapes = readFile(args[0]);
        var drawing = new Drawing(shapes);
        var paint = new Paint(drawing.windowSize().width(), drawing.windowSize().height());
        SimpleGraphics area = new SimpleGraphics("area", paint.width, paint.height);
        drawing.drawAll(area);
        area.waitForMouseEvents((x,y) -> drawing.onClick(area,x,y));
        //area.render(graphics -> Paint.drawAll(graphics,paint.allShapes));
    }

}
