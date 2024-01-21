package fr.uge.poo.cmdline.ex1;

import java.util.*;

public class CmdLineParser {

    private final HashMap<String, Runnable> registeredOptions = new HashMap<>();

    public void registerOption(String option, Runnable runnable) {
        Objects.requireNonNull(option);
        Objects.requireNonNull(runnable);
        if(registeredOptions.containsKey(option)){
            throw new IllegalStateException("the name " + option + "is already exist");
        }
        registeredOptions.put(option, runnable);
    }

/*    public Set<String> getOptionsSeen() {
        return Collections.unmodifiableSet(optionsSeen);
    }*/

    public List<String> process(String[] arguments) {
        ArrayList<String> files = new ArrayList<>();
        for (String argument : arguments) {
            var act = registeredOptions.get(argument);
            if (act != null) {
                act.run();
            } else {
                files.add(argument);
            }
        }
        return files;
    }
}