package fr.uge.poo.cmdline.ex3;

import java.nio.file.Path;
import java.util.List;

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
            private int borderWidth = 0;
            private String windowName = "";

            public Builder legacy(boolean legacy){
                this.legacy = legacy;
                return this;
            }

            public Builder bordered(boolean bordered){
                this.bordered = bordered;
                return this;
            }

            public Builder setBorderWidth(int width) {
                this.borderWidth = width;
                return this;
            }

            public Builder setWindowName(String s) {
                this.windowName = s;
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
        cmdParser.addOptionsWithOneParameter("border-width", (s) -> settingsBuilder.setBorderWidth(Integer.parseInt(s)));
        cmdParser.addOptionsWithOneParameter("window-name", settingsBuilder::setWindowName);
        List<Path> files = cmdParser.process(arguments);
        // this code replaces the rest of the application
        files.forEach(System.out::println);
        var settings = settingsBuilder.build();

        System.out.println(settings);

    }
}
