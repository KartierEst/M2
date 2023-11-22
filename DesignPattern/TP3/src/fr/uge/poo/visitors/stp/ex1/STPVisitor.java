package fr.uge.poo.visitors.stp.ex1;

public interface STPVisitor {
    void visit(HelloCmd parser);

    void visit(StartTimerCmd parser);

    void visit(StopTimerCmd parser);

    void visit(ElapsedTimeCmd parser);
}
