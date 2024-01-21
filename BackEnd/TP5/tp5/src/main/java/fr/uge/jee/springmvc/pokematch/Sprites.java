package fr.uge.jee.springmvc.pokematch;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sprites{
    private Url sprites;

    public Url getSprites() {
        return sprites;
    }

    public void setSprites(Url sprites) {
        this.sprites = sprites;
    }
}
