package fr.uge.poo.cmdline.ex4;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        var settingsBuilder = ApplicationSettings.builder();
        String[] arguments={"-name","abder","-leg","-aa","filename1","filename2","-border-width","17","-min-size","700","700","-remote-server","httpLocal","8080"};
        var cmdParser = new CmdLineParser();
        cmdParser.addOption(new Option.OptionBuilder()
                .setListConsumer(x -> settingsBuilder.legacy(true))
                .setNames(List.of("-legacy","-leg"))
                .setDocumentation("the legacy")
                .build());

        cmdParser.addOption(new Option.OptionBuilder()
                .addName("-name")
                .setNbParam(1)
                .setListConsumer(windowName -> settingsBuilder.setWindowName(windowName.get(0)))
                .setDocumentation("name of the window")
                .build());

        cmdParser.addOption(new Option.OptionBuilder()
                .addName("-border-width")
                .setNbParam(1)
                .oneIntOption(settingsBuilder::setBorderWidth)
                .setDocumentation("the border width of the window if you have border")
                .build());

        cmdParser.addOption(new Option.OptionBuilder()
                .addName("-min-size")
                .setNbParam(2)
                .TwoIntOption((x,y) -> {
                    settingsBuilder.setHeight(x);
                    settingsBuilder.setWidth(y);
                })
                .setDocumentation("size of the window")
                .build());

        cmdParser.addOption(new Option.OptionBuilder()
                .addName("-remote-server")
                .setNbParam(2)
                .InetSocketAdressOption(settingsBuilder::setServer)
                .setDocumentation("the socket of the server")
                .build());


        cmdParser.registerOption("-no-borders", () -> settingsBuilder.bordered(false), "pas de bordure");
        cmdParser.addOptionWithParameters("-aa",argv -> System.out.println(argv.get(0) + " " + argv.get(1)),true, 2, "test prise de 2 paramètres");
        cmdParser.process(arguments);
        System.out.println(settingsBuilder.build().toString());
        cmdParser.usage();

    }
}