package fr.uge.poo.visitors.stp.stphipster;

public sealed interface STPCommand permits HelloCmd, StartTimerCmd, StopTimerCmd, ElapsedTimeCmd {
}
