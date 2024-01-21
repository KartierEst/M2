package fr.uge.poo.cmdline.ex3;
import fr.uge.poo.cmdline.ex2.Application;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Option {
    private final String name;
    private final int nbParam;
    private final Consumer<List<String>> listConsumer;
    private final boolean required;

    private Option(OptionBuilder optionBuilder) {
        Objects.requireNonNull(optionBuilder);
        this.name = optionBuilder.name;
        this.nbParam = optionBuilder.nbParam;
        this.listConsumer = optionBuilder.listConsumer;
        this.required = optionBuilder.required;
    }

    public static OptionBuilder with() {
        return new OptionBuilder();
    }

    public static class OptionBuilder {
        private String name;
        private int nbParam = 0;
        private Consumer<List<String>> listConsumer;
        private boolean required = false;

        public OptionBuilder setName(String name){
            Objects.requireNonNull(name);
            this.name = name;
            return this;
        }

        public OptionBuilder setNbParam(int nbParam){
            if(nbParam < 0){
                throw new IllegalStateException("number of parameters is negative");
            }
            this.nbParam = nbParam;
            return this;
        }

        public OptionBuilder setListConsumer(Consumer<List<String>> listConsumer){
            Objects.requireNonNull(listConsumer);
            this.listConsumer = listConsumer;
            return this;
        }

        public OptionBuilder setRequired(boolean required){
            this.required = required;
            return this;
        }

        public Option build(){
            if(this.name == null){
                throw new IllegalStateException("no file name");
            }
            if(this.listConsumer == null){
                throw new IllegalStateException("option is null");
            }
            if(this.nbParam < 0){
                throw new IllegalStateException("number of parameters is negative");
            }
            return new Option(this);
        }
    }
    int getNbParam(){
        return nbParam;
    }
    Consumer<List<String>> getListConsumer(){
        return listConsumer;
    }
    boolean isRequired(){
        return required;
    }
    String getName(){
        return name;
    }
}
