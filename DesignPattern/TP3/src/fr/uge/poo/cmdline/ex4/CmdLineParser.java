package fr.uge.poo.cmdline.ex4;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CmdLineParser {

    private final Map<String, Option> optionAndParameter = new HashMap<>();


    public void usage() {
        var stringBuilder = new StringBuilder();
        var options = new HashSet<>(optionAndParameter.values());
        var mainNames = options
                .stream()
                .map(it -> it.getNames().get(0) + " arg".repeat(Math.max(0, it.getNbParam())))
                .toList();
        stringBuilder
                .append("Usage : command ")
                .append(mainNames.stream().collect(Collectors.joining("] [", "[", "]")))
                .append('\n');

        for (var option : options) {
            stringBuilder
                    .append("\t")
                    .append(String.join(", ", option.getNames()))
                    .append(" : ")
                    .append(option.getDocumentation())
                    .append(option.isRequired() ? " (REQUIRED)" : "")
                    .append("\n");
        }
        System.out.println(stringBuilder);
    }

    public void registerOption(String option, Runnable runnable, boolean required, String doc) {
        Objects.requireNonNull(option);
        Objects.requireNonNull(runnable);
        Objects.requireNonNull(doc);
        var getOpt = optionAndParameter.get(option);
        if(getOpt != null){
            throw new IllegalStateException("the name " + option + "is already exist");
        }
        optionAndParameter.put(option, Option.with().setDocumentation(doc).addName(option).setRequired(required).setListConsumer(x -> runnable.run()).build());
    }

    public void registerOption(String option, Runnable process, boolean required) throws IllegalStateException {
        registerOption(option, process, required, "vide");
    }

    public void registerOption(String option, Runnable process) throws IllegalStateException {
        registerOption(option, process, false);
    }

    public void registerOption(String option, Runnable process, String doc) throws IllegalStateException {
        registerOption(option, process, false, doc);
    }


    public void addOptionsWithOneParameter(String option, Consumer<String> consumer, boolean required, String doc){
        Objects.requireNonNull(option);
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(doc);
        var getOpt = optionAndParameter.get(option);
        if(getOpt != null){
            throw new IllegalStateException("the name " + option + "is already exist");
        }
            optionAndParameter.put(option, Option.with().setDocumentation(doc).addName(option).setRequired(required).setNbParam(1).setListConsumer(x -> consumer.accept(x.get(0))).build());
    }

    public void addOptionsWithOneParameter(String option, Consumer<String> consumer, boolean required){
        addOptionsWithOneParameter(option, consumer, required, "vide");
    }
    public void addOptionsWithOneParameter(String option, Consumer<String> consumer){
        addOptionsWithOneParameter(option, consumer, false);
    }

    public void addOptionWithParameters(String option, Consumer<List<String>> consumer, boolean required, int nbparam, String doc) throws IllegalArgumentException {
        Objects.requireNonNull(option);
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(doc);
        var getOpt = optionAndParameter.get(option);
        if(getOpt != null){
            throw new IllegalStateException("the name " + option + "is already exist");
        }
        var objectBuilder = new Option.OptionBuilder();
        objectBuilder.setDocumentation(doc).setRequired(required).setListConsumer(consumer).setNbParam(nbparam).addName(option);
        optionAndParameter.put(option,objectBuilder.build());
    }

    public void addOptionWithParameters(String option, Consumer<List<String>> consumer, boolean required, int nbparam) throws IllegalArgumentException {
        addOptionWithParameters(option, consumer, required, nbparam, "vide");
    }
    public void addOptionWithParameters(String option, Consumer<List<String>> consumer, int nbparam) throws IllegalArgumentException {
        addOptionWithParameters(option, consumer, false, nbparam, "vide");
    }

    public void addOption(Option option){
        Objects.requireNonNull(option);
        for (var name : option.getNames()) {
            if(name.charAt(0) != '-'){
                throw new IllegalStateException("an option begin with '-'");
            }
            optionAndParameter.put(name, option);
        }
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
                    if(param.charAt(0) == '-'){
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