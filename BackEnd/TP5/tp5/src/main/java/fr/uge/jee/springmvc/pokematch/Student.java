package fr.uge.jee.springmvc.pokematch;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Student {
    @NotNull
    @NotEmpty
    @NotBlank(message = "pas d'espace")
    @Pattern(regexp = "[a-zA-Z]+", message = "Le firstname doit être un string")
    private String name = "";
    @NotNull
    @NotEmpty
    @NotBlank(message = "pas d'espace")
    @Pattern(regexp = "[a-zA-Z]+", message = "Le firstname doit être un string")
    private String firstName = "";

    private Pokemon pokemon;

    public Student(){}
    public Student(String name,String firstName) {
        this.firstName = firstName;
        this.name = name;
    }

    public Pokemon yourPokemon(Pokemons pokemons){
        Pokemon pokemon = null;
        var hash = (name + firstName).hashCode();
        var minDiff = Integer.MAX_VALUE;
        for(var i : pokemons.getPokemons()){
            var diff = Math.abs(i.getName().hashCode() - hash);

            if(diff < minDiff){
                pokemon = i;
                minDiff = diff;
            }
        }
        return pokemon;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
