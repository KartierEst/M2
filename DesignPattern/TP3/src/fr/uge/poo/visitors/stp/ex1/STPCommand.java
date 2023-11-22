package fr.uge.poo.visitors.stp.ex1;

public sealed interface STPCommand permits HelloCmd, StartTimerCmd, StopTimerCmd, ElapsedTimeCmd {

    void accept(STPVisitor visitor);
}
