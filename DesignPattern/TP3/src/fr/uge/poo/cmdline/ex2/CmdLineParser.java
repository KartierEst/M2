package fr.uge.poo.cmdline.ex2;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public class CmdLineParser {

    private final HashMap<String, Consumer<Iterator<String>>> registeredOptions = new HashMap<>();
    // iterator = liste de string

    public void registerOption(String option, Runnable runnable) {
        Objects.requireNonNull(option);
        Objects.requireNonNull(runnable);
        if(registeredOptions.containsKey(option)){
            throw new IllegalStateException("the name " + option + "is already exist");
        }
        // runnable en iterator
        registeredOptions.put(option, stringIterator -> runnable.run());
    }

/*    public Set<String> getOptionsSeen() {
        return Collections.unmodifiableSet(optionsSeen);
    }*/

    public void addOptionsWithOneParameter(String option, Consumer<String> consumer){
        Objects.requireNonNull(option);
        Objects.requireNonNull(consumer);
        if(registeredOptions.containsKey(option)){
            throw new IllegalStateException();
        }
        registeredOptions.put(option, stringIterator -> {
           if(!stringIterator.hasNext()){
               throw new IllegalStateException();
           }
           var param = stringIterator.next();
           try {
               consumer.accept(param);
           } catch (Exception e){
               throw new IllegalStateException();
           }
        });
    }

    public List<Path> process(String[] arguments) {
        ArrayList<Path> files = new ArrayList<>();
        var i = List.of(arguments).iterator();
        while (i.hasNext()) {
            var arg = i.next();
            var consumer = registeredOptions.get(arg);
            if (consumer != null) {
                consumer.accept(i);
            } else {
                files.add(Path.of(arg));
            }
        }
        return files;
    }
}