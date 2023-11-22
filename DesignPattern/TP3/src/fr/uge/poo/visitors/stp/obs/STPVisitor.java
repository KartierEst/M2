package fr.uge.poo.visitors.stp.obs;

public interface STPVisitor {
    void visit(HelloCmd parser);

    void visit(StartTimerCmd parser);

    void visit(StopTimerCmd parser);

    void visit(ElapsedTimeCmd parser);
}
