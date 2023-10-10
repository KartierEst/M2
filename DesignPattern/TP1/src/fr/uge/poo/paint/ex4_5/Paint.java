package fr.uge.poo.paint.ex4_5;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Paint {

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
        SimpleGraphics area = new SimpleGraphics("area", 800, 600);
        drawing.drawAll(area);
        area.waitForMouseEvents((x,y) -> drawing.onClick(area,x,y));
        //area.render(graphics -> Paint.drawAll(graphics,paint.allShapes));
    }

}
