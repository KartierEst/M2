package fr.uge.poo.cmdline.ex1;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    // The record ApplicationSettings is used to store the settings of the application,
    // does the application start with a border, does it uses the legacy drawing engine.
    public record ApplicationSettings(boolean legacy, boolean bordered){

        public static Builder builder() {
            return new Builder();
        }

        static public class Builder {

            private boolean legacy = false;
            private boolean bordered = true;

            public Builder legacy(boolean legacy){
                this.legacy = legacy;
                return this;
            }

            public Builder bordered(boolean bordered){
                this.bordered = bordered;
                return this;
            }

            public ApplicationSettings build(){
                return new ApplicationSettings(legacy,bordered);
            }
        }

    };



    public static void main(String[] args) {
        var settingsBuilder = ApplicationSettings.builder();
        String[] arguments={"-legacy","-no-borders","filename1","filename2"};
        var cmdParser = new CmdLineParser();
        cmdParser.registerOption("-legacy", () -> settingsBuilder.legacy(true));
        cmdParser.registerOption("-with-borders", () -> settingsBuilder.bordered(true));
        cmdParser.registerOption("-no-borders", () -> settingsBuilder.bordered(false));
        List<String> result = cmdParser.process(arguments);
        List<Path> files = result.stream().map(Path::of).toList();
        // this code replaces the rest of the application
        files.forEach(System.out::println);
        var settings = settingsBuilder.build();
        System.out.println(settings);

    }
}
