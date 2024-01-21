package fr.uge.poo.cmdline.ex3;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public class CmdLineParser {

    private final Map<String,Option> optionAndParameter = new HashMap<>();

    public void registerOption(String option, Runnable runnable, boolean required) {
        Objects.requireNonNull(option);
        Objects.requireNonNull(runnable);
        var getOpt = optionAndParameter.get(option);
        if(getOpt != null){
            throw new IllegalStateException("the name " + option + "is already exist");
        }
        optionAndParameter.put(option, Option.with().setName(option).setRequired(required).setListConsumer(x -> runnable.run()).build());
    }


    public void registerOption(String option, Runnable process) throws IllegalStateException {
        registerOption(option, process, false);
    }


    public void addOptionsWithOneParameter(String option, Consumer<String> consumer, boolean required){
        Objects.requireNonNull(option);
        Objects.requireNonNull(consumer);
        var getOpt = optionAndParameter.get(option);
        if(getOpt != null){
            throw new IllegalStateException("the name " + option + "is already exist");
        }
        optionAndParameter.put(option, Option.with().setName(option).setRequired(required).setNbParam(1).setListConsumer(x -> consumer.accept(x.get(0))).build());
    }

    public void addOptionsWithOneParameter(String option, Consumer<String> consumer){
        addOptionsWithOneParameter(option, consumer, false);
    }

    public void addOptionWithParameters(String option, Consumer<List<String>> consumer, boolean required, int nbparam) throws IllegalArgumentException {
        Objects.requireNonNull(option);
        Objects.requireNonNull(consumer);
        var getOpt = optionAndParameter.get(option);
        if(getOpt != null){
            throw new IllegalStateException("the name " + option + "is already exist");
        }
        var objectBuilder = new Option.OptionBuilder();
        objectBuilder.setRequired(required).setListConsumer(consumer).setNbParam(nbparam).setName(option);
        optionAndParameter.put(option,objectBuilder.build());
    }

    public void addOptionWithParameters(String option, Consumer<List<String>> consumer, int nbparam) throws IllegalArgumentException {
        addOptionWithParameters(option, consumer, false, nbparam);
    }

    public void addOption(Option option){
        Objects.requireNonNull(option);
        if(option.getName().charAt(0) != '-'){
            throw new IllegalStateException("it's not a option");
        }
        optionAndParameter.put(option.getName(),option);
    }

    public List<Path> process(String[] arguments) {
        ArrayList<Path> files = new ArrayList<>();
        var i = List.of(arguments).iterator();
        while (i.hasNext()) {
            var option = i.next();
            var consumer = optionAndParameter.get(option);
            if (consumer != null) {
                if(option.charAt(0) != '-'){
                    throw new IllegalStateException("is not a option because don't start with '-'");
                }
                var parameters = new ArrayList<String>();
                for(int j = 0; j < consumer.getNbParam(); j++){
                    if(!i.hasNext()){
                        throw new IllegalStateException("missing arguments");
                    }
                    var param = i.next();
                    if(option.charAt(0) == '-'){
                        throw new IllegalStateException("an option is not a argument to an option");
                    }
                    parameters.add(param);
                }
                consumer.getListConsumer().accept(parameters);
            } else {
                files.add(Path.of(option));
            }
        }
        return files;
    }
}