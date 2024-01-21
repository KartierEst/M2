package fr.uge.jee.springmvc.pokematch;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class PokemonImage {

    private WebClient webClient; //= getWebClient(WebClient.builder());

    private Map<Pokemon, Optional<byte[]>> images;

    private final static String nameRequestQL = "{ \"query\": \"query get { results: pokemon_v2_pokemonsprites(limit: 40) { data: pokemon_v2_pokemon { name }} }\" }";
    private final static String startSpritesRequestQL = "{ \"query\": \"query get { results: pokemon_v2_pokemonsprites(where: {pokemon_v2_pokemon: {name: {_eq:";
    private final static String endSpritesRequestQL = "}}}) {sprites}}\" }";


    public PokemonImage(WebClient webClient) {
        this.webClient = webClient;
        var jsonElements = new com.google.gson.JsonParser().parse(getPokemonNamesGraphQL()).getAsJsonObject()
                .get("data").getAsJsonObject()
                .get("results").getAsJsonArray();
        this.images = IntStream.range(0, 40)
                .mapToObj(x -> new Pokemon(x,jsonElements.get(x).getAsJsonObject().get("data").getAsJsonObject().get("name").getAsString()))
                .collect(Collectors.toMap(x -> x, x -> Optional.empty()));
    }
/*    public Mono<Pokemon> getPokemon(int id) {
        return webClient.get()
                .uri("https://pokeapi.co/api/v2/pokemon/"+id)
                .retrieve()
                .bodyToMono(Pokemon.class);
    }*/

    private String getPokemonNamesGraphQL() {
        return webClient.post()
                .uri("https://beta.pokeapi.co/graphql/v1beta")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(nameRequestQL)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    public byte[] getPokemonSpriteGraphQL(Pokemon pokemon) throws IOException {
        if(images.get(pokemon).isEmpty()){
            var sprite = webClient.post()
                    .uri("https://beta.pokeapi.co/graphql/v1beta")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(startSpritesRequestQL + pokemon.getName() + endSpritesRequestQL)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            var url = translateSprite(
                    String.valueOf(
                            new JsonParser().parse(Objects.requireNonNull(sprite)).getAsJsonObject()
                                    .get("data").getAsJsonObject()
                                    .get("results").getAsJsonArray()
                                    .get(0).getAsJsonObject()
                                    .get("sprites")));
            images.put(pokemon, Optional.of(IOUtils.toByteArray(new URL(url))));
        }
        return images.get(pokemon).get();
    }
    private String translateSprite(String sprite) {
        var url = sprite.substring(1, sprite.length() - 1)
                .substring(22)
                .substring(0, 78);
        if(!url.endsWith("g")){
            url += "g";
        }
        return url;
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public Map<Pokemon, Optional<byte[]>> getImages() {
        return images;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void setImages(Map<Pokemon, Optional<byte[]>> images) {
        this.images = images;
    }
}