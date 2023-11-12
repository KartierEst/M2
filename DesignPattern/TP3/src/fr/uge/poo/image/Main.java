package fr.uge.poo.image;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        var map = Map.of("cat","http://www.example.com/cat.png",
                "dog","http://www.example.com/dog.png",
                "mice","http://www.example.com/mice.png");
        var images =map.values().stream().map(Image::download).toList();
        System.out.println(images.get(0).hue());
    }
}
