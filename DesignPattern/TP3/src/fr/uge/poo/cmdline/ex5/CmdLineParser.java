package fr.uge.poo.cmdline.ex5;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CmdLineParser {

    private final OptionsManager manager;
    private final DocumentationObserver documentationObserver;
    private int counterListDoc = 0;
    public CmdLineParser() {
        manager = new OptionsManager();
        documentationObserver = new DocumentationObserver();
        manager.registerObserver(new LoggerObserver());
        manager.registerObserver(new RequiredObserver());
        manager.registerObserver(documentationObserver);
    }

    interface OptionsManagerObserver {

        void onRegisteredOption(OptionsManager optionsManager,Option option);

        void onProcessedOption(OptionsManager optionsManager,Option option);

        void onFinishedProcess(OptionsManager optionsManager);
    }

    private static class OptionsManager {

        private final HashMap<String, Option> options = new HashMap<>();
        private final ArrayList<OptionsManagerObserver> managerObservers = new ArrayList<>();

        void registerObserver(OptionsManagerObserver managerObserver){
            managerObservers.add(Objects.requireNonNull(managerObserver));
        }

        /**
         * Register the option with all its possible names
         * @param option
         */

        void register(Option option) {
            for (var alias : option.getNames()){
                register(alias, option);
            }
        }

        private void register(String name, Option option) {
            if (options.containsKey(name)) {
                throw new IllegalStateException("Option " + name + " is already registered.");
            }
            for (var observer : managerObservers) {
                observer.onRegisteredOption(this, option);
            }
            options.put(name, option);
        }

        /**
         * This method is called to signal that an option is encountered during
         * a command line process
         *
         * @param optionName
         * @return the corresponding object option if it exists
         */

        Optional<Option> processOption(String optionName) {
            var option = Optional.ofNullable(options.get(optionName));
            if (option.isPresent()) {
                for (var observer : managerObservers) {
                    observer.onProcessedOption(this, option.get());
                }
            }
            return option;
        }

        /**
         * This method is called to signal the method process of the CmdLineParser is finished
         */
        void finishProcess() {
            for (var observer : managerObservers) {
                observer.onFinishedProcess(this);
            }
        }
    }


    private class LoggerObserver implements OptionsManagerObserver {

        @Override
        public void onRegisteredOption(OptionsManager optionsManager, Option option) {
            System.out.println("Option "+option+ " is registered");
        }

        @Override
        public void onProcessedOption(OptionsManager optionsManager, Option option) {
            System.out.println("Option "+option+ " is processed");
        }

        @Override
        public void onFinishedProcess(OptionsManager optionsManager) {
            System.out.println("Process method is finished");
        }
    }

    private class RequiredObserver implements OptionsManagerObserver {

        private final HashSet<Option> required = new HashSet<>();
        private final HashSet<Option> seen = new HashSet<>();

        @Override
        public void onRegisteredOption(OptionsManager optionsManager, Option option) {
            if (!option.isRequired()){
                return;
            }
            required.add(option);
        }

        @Override
        public void onProcessedOption(OptionsManager optionsManager, Option option) {
            seen.add(option);
        }

        @Override
        public void onFinishedProcess(OptionsManager optionsManager)  {
            for (var elem : required) {
                if (!(seen.contains(elem))) {
                    throw new IllegalStateException("Missing required option : " + elem.getNames().get(0));
                }
            }
        }
    }
    private class DocumentationObserver implements OptionsManagerObserver {
        private final StringBuilder usage = new StringBuilder().append('\n');
        @Override
        public void onRegisteredOption(OptionsManager optionsManager, Option option) {
            if(option.getNames().size() > 1){
                if(counterListDoc == 0){
                    usage.append("\t")
                            .append(String.join(", ", option.getNames()))
                            .append(" : ")
                            .append(option.getDocumentation())
                            .append(option.isRequired() ? " (REQUIRED)" : "")
                            .append("\n");
                    counterListDoc++;
                }
            }
            else {
                usage.append("\t")
                        .append(option.getNames().get(0))
                        .append(" : ")
                        .append(option.getDocumentation())
                        .append(option.isRequired() ? " (REQUIRED)" : "")
                        .append("\n");
                counterListDoc = 0;
            }

        }

        @Override
        public void onProcessedOption(OptionsManager optionsManager, Option option){
        }

        @Override
        public void onFinishedProcess(OptionsManager optionsManager) {
        }

        String usage() { return usage.toString(); }
    }


    public void usage() {
        var stringBuilder = new StringBuilder();
        var options = new HashSet<>(manager.options.values());
        var mainNames = options
                .stream()
                .map(it -> it.getNames().get(0) + " arg".repeat(Math.max(0, it.getNbParam())))
                .toList();
        stringBuilder
                .append("Usage : command ")
                .append(mainNames.stream().collect(Collectors.joining("] [", "[", "]")))
                .append('\n');
        System.out.println(stringBuilder + documentationObserver.usage());
    }

    public void registerOption(String option, Runnable runnable, boolean required, String doc) {
        Objects.requireNonNull(option);
        Objects.requireNonNull(runnable);
        Objects.requireNonNull(doc);
        manager.register(option, Option.with().setDocumentation(doc).addName(option).setRequired(required).setListConsumer(x -> runnable.run()).build());
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
        manager.register(option, Option.with().setDocumentation(doc).addName(option).setRequired(required).setNbParam(1).setListConsumer(x -> consumer.accept(x.get(0))).build());
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
        manager.register(option,Option.with().setDocumentation(doc).setRequired(required).setListConsumer(consumer).setNbParam(nbparam).addName(option).build());
    }

    public void addOptionWithParameters(String option, Consumer<List<String>> consumer, boolean required, int nbparam) throws IllegalArgumentException {
        addOptionWithParameters(option, consumer, required, nbparam, "vide");
    }
    public void addOptionWithParameters(String option, Consumer<List<String>> consumer, int nbparam) throws IllegalArgumentException {
        addOptionWithParameters(option, consumer, false, nbparam, "vide");
    }

    public void addOption(Option option){
        manager.register(option);
    }

    public List<Path> process(String[] arguments) {
        ArrayList<Path> files = new ArrayList<>();
        var i = List.of(arguments).iterator();
        while (i.hasNext()) {
            var option = i.next();
            var tmp = manager.processOption(option);
            if (tmp.isPresent()) {
                var opt = tmp.get();
                var parameters = new ArrayList<String>();
                for(int j = 0; j < opt.getNbParam(); j++){
                    if(!i.hasNext()){
                        throw new IllegalStateException("missing arguments");
                    }
                    var param = i.next();
                    if(param.charAt(0) == '-'){
                        throw new IllegalStateException("an option is not a argument to an option");
                    }
                    parameters.add(param);
                }
                opt.getListConsumer().accept(parameters);
            } else {
                files.add(Path.of(option));
            }
        }
        manager.finishProcess();
        return files;
    }
}