package fr.uge.poo.visitors.stp.obs;

public sealed interface STPCommand permits HelloCmd, StartTimerCmd, StopTimerCmd, ElapsedTimeCmd {

    void accept(STPVisitor visitor);
    String toString();
}
