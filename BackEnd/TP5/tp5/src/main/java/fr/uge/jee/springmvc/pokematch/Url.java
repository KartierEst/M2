package fr.uge.jee.springmvc.pokematch;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Url {
    @JsonProperty("front_default")
    private String url;
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
