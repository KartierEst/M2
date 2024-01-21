package fr.uge.jee.springmvc.pokematch;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Pokemons implements GraphQLQueryResolver {
    private final PokemonImage pokemonImage;
    private ArrayDeque<Pokemon> pokemons = new ArrayDeque<>();
    private final TreeMap<Integer, Set<Pokemon>> topPoke = new TreeMap<>(Comparator.reverseOrder());
    private final Object lock = new Object();

    public List<Pokemon> pokemons() {
        return pokemonImage.getImages().keySet().stream().toList();
    }

    public Pokemons(PokemonImage pokemonImage) {
        this.pokemonImage = pokemonImage;
        pokemons = new ArrayDeque<>(pokemons());
    }


/*    Mono<Pokemon> getPokemon(int id){
        return pokemonImage.getPokemon(id);
    }*/

    public ArrayDeque<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(ArrayDeque<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public Pokemon get(int id){
        synchronized (lock) {
            return pokemons.stream().toList().get(id);
        }
    }

    public void sortedInsert(Pokemon pokemon){
        synchronized (lock) {
            var set = topPoke.get(pokemon.getCounter());
            if(set != null) {
                set.remove(pokemon);
                topPoke.put(pokemon.getCounter(), set);
            }
            var incr = pokemon.increment();
            var newSet = topPoke.get(incr);
            if (newSet == null) {
                var set2 = new HashSet<Pokemon>();
                set2.add(pokemon);
                topPoke.put(incr, set2);
                return;
            }
            newSet.add(pokemon);
            topPoke.put(incr, newSet);
        }
    }

    public ArrayDeque<Pokemon> getTop10(){
        List<Pokemon> list = topPoke.values().stream().flatMap(Collection::stream).limit(10).toList();
        return new ArrayDeque<>(list);
    }

    @Override
    public String toString() {
        return "Pokemons{" +
                "pokemons=" + pokemons +
                '}';
    }
}
