package fr.uge.jee.springmvc.pokematch;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Base64;

@Controller
public class PokemonController {

    private final Pokemons pokemons;
    private final PokemonImage images;

    public PokemonController(Pokemons pokemons, PokemonImage images) {
        this.pokemons = pokemons;
        this.images = images;
    }

    @GetMapping("/form-pokemon")
    public String formPokemon(Model model){
        model.addAttribute("student", new Student());
        if(!pokemons.getTop10().isEmpty()){
            ArrayDeque<Pokemon> top10 = pokemons.getTop10();
            model.addAttribute("top10", top10);
        }
        return "form-pokemon";
    }

    @PostMapping("/form-pokemon")
    public String processForm(@Valid @ModelAttribute("student") Student student,
                              BindingResult result,
                              Model model) throws IOException {
        if (result.hasErrors()){
            return "form-pokemon";
        }
        var yourPokemon = student.yourPokemon(pokemons);
        pokemons.sortedInsert(yourPokemon);
        model.addAttribute("pokemon", yourPokemon.getName());
        ArrayDeque<Pokemon> top10 = pokemons.getTop10();
        model.addAttribute("top10", top10);
        addImageAttribute(yourPokemon,model);
        return "form-pokemon";
    }

    private void addImageAttribute(Pokemon pokemon, Model model) throws IOException {
        var img = Base64.getEncoder().encodeToString(images.getPokemonSpriteGraphQL(pokemon));
        model.addAttribute("imagePoke", img);
    }
}
