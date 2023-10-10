package fr.uge.poo.paint.ex3;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Paint {
    private final ArrayList<Shape> allShapes = new ArrayList<>();

    private final String fileName;
    public Paint(String fileName) {
        this.fileName = fileName;
    }

    public void readFile() throws IOException {
        Path path = Paths.get(fileName);
        try(Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                String[] tokens = line.split(" ");
                allShapes.add(createShape(tokens));
            });
        }
    }

    public Shape createShape(String[] tokens){
        switch (tokens[0]) {
            case "line" -> {
                int x1 = Integer.parseInt(tokens[1]);
                int y1 = Integer.parseInt(tokens[2]);
                int x2 = Integer.parseInt(tokens[3]);
                int y2 = Integer.parseInt(tokens[4]);
                return new Line(x1, y1, x2, y2);
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

    public static void drawAll(Graphics2D graphics, ArrayList<Shape> shapes) {
        graphics.setColor(Color.BLACK);
        for(var shape : shapes){
            System.out.println(shape);
            shape.draw(graphics);
        }
    }

    public static void main(String[] args) throws IOException {
        var paint = new Paint(args[0]);
        paint.readFile();
        SimpleGraphics area = new SimpleGraphics("area", 800, 600);
        area.clear(Color.WHITE);
        area.render(graphics -> Paint.drawAll(graphics,paint.allShapes));
    }

}
