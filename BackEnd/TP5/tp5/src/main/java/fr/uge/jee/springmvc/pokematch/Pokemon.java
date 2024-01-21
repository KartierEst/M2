package fr.uge.jee.springmvc.pokematch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

public class Pokemon {

    //@JsonProperty("id")
    private int id;
    //@JsonProperty("name")
    private String name;

    //@JsonProperty("sprites")
/*
    @JsonProperty("pokemon_v2_pokemonsprites")
    private List<SpritesWrapper> sprites;
*/

    //private final HashMap<String, byte[]> images = new HashMap<>();

    private int count = 0;
    private final Object lock = new Object();

    public Pokemon(){
    }

    public Pokemon(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {return id;}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

/*    public List<SpritesWrapper> getSprites() {
        return sprites;
    }

    public void setSprites(List<SpritesWrapper> sprites) {
        this.sprites = sprites;
    }*/

    @Override
    public String toString() {
        return id + " " + name + " " + count;
    }

    public int increment(){
        synchronized (lock){
            count++;
            return count;
        }
    }

    public int getCounter(){
        synchronized (lock){
            return count;
        }
    }

/*    public void addImage(Pokemon pokemon, byte[] image){
        synchronized (lock){
            images.putIfAbsent(pokemon.getName(), image);
        }
    }

    public byte[] getImage(Pokemon pokemon){
        synchronized (lock){
            return images.get(pokemon.getName());
        }
    }*/
}
