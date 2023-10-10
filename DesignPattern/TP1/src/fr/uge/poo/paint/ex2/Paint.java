package fr.uge.poo.paint.ex2;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Paint {
    private final ArrayList<Line> allLines = new ArrayList<>();
    private final String fileName;
    public Paint(String fileName) {
        this.fileName = fileName;
    }
    public void readFile() throws IOException {
        Path path = Paths.get(fileName);
        try(Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                String[] tokens = line.split(" ");
                int x1 = Integer.parseInt(tokens[1]);
                int y1 = Integer.parseInt(tokens[2]);
                int x2 = Integer.parseInt(tokens[3]);
                int y2 = Integer.parseInt(tokens[4]);
                System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
                allLines.add(new Line(x1,y1,x2,y2));
            });
        }
    }

    public static void draw(Graphics2D graphics, ArrayList<Line> lines) {
        graphics.setColor(Color.BLACK);
        for(var line : lines){
            line.draw(graphics);
        }
    }

    public static void main(String[] args) throws IOException {
        var paint = new Paint(args[0]);
        paint.readFile();
        SimpleGraphics area = new SimpleGraphics("area", 800, 600);
        area.clear(Color.WHITE);
        area.render(graphics -> Paint.draw(graphics, paint.allLines));
    }

}
