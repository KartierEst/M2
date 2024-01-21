package fr.uge.poo.cmdline.ex5;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class Option {
    private final List<String> names;
    private final int nbParam;
    private final Consumer<List<String>> listConsumer;
    private final boolean required;
    private final String documentation;

    private Option(OptionBuilder optionBuilder) {
        Objects.requireNonNull(optionBuilder);
        this.names = optionBuilder.names;
        this.nbParam = optionBuilder.nbParam;
        this.listConsumer = optionBuilder.listConsumer;
        this.required = optionBuilder.required;
        this.documentation = optionBuilder.documentation;
    }

    public static OptionBuilder with() {
        return new OptionBuilder();
    }

    public static class OptionBuilder {
        // private String name;
        private List<String> names = new ArrayList<>();
        private int nbParam = 0;
        private Consumer<List<String>> listConsumer;
        private boolean required = false;
        private String documentation;

        public OptionBuilder setNames(List<String> names){
            Objects.requireNonNull(names);
            this.names = names;
            return this;
        }
        public OptionBuilder addName(String name){
            Objects.requireNonNull(name);
            names.add(name);
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

        public OptionBuilder setDocumentation(String documentation) {
            Objects.requireNonNull(documentation);
            this.documentation = documentation;
            return this;
        }

        public OptionBuilder oneIntOption(IntConsumer consumer){
            setListConsumer(x -> {
                try{
                    var num = x.get(0);
                    consumer.accept(Integer.parseInt(num));
                } catch(NumberFormatException e){
                    throw new IllegalStateException("the parameter is not a number");
                }
            });
            return this;
        }

        public OptionBuilder TwoIntOption(BiConsumer<Integer,Integer> consumer){
            setListConsumer(x -> {
                try{
                    var num1 = x.get(0);
                    var num2 = x.get(1);
                    consumer.accept(Integer.parseInt(num1),Integer.parseInt(num2));
                } catch(NumberFormatException e){
                    throw new IllegalStateException("one of parameter is not a number");
                }
            });
            return this;
        }

        public OptionBuilder InetSocketAdressOption(BiConsumer<String,Integer> consumer){
            setListConsumer(x -> {
                try{
                    var name = x.get(0);
                    var num = x.get(1);
                    consumer.accept(name, Integer.parseInt(num));
                } catch(NumberFormatException e){
                    throw new IllegalStateException("the server port id is not a number");
                }
            });
            return this;
        }


        public Option build(){
            if(this.names.isEmpty()){
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
    List<String> getNames(){
        return names;
    }
    String getDocumentation(){ return documentation; }

    @Override
    public String toString() {
        return "{" +
                "names=" + names + '}';
    }
}
