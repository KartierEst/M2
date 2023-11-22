package fr.uge.poo.visitors.stp.stphipster;

public interface STPVisitor {
    void visit(HelloCmd parser);

    void visit(StartTimerCmd parser);

    void visit(StopTimerCmd parser);

    void visit(ElapsedTimeCmd parser);

    default void accept(STPCommand command){
        switch (command){
            case HelloCmd helloCmd -> visit(helloCmd);
            case StartTimerCmd startTimerCmd -> visit(startTimerCmd);
            case StopTimerCmd stopTimerCmd -> visit(stopTimerCmd);
            case ElapsedTimeCmd elapsedTimeCmd -> visit(elapsedTimeCmd);
            default -> throw new IllegalStateException("Unexpected value: " + command);
        }
    }

}
