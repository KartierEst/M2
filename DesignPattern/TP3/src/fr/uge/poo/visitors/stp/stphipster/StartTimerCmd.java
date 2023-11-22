package fr.uge.poo.visitors.stp.stphipster;

public record StartTimerCmd(int timerId) implements STPCommand {
    public StartTimerCmd {
        if(timerId < 0){
            throw new IllegalArgumentException("timer id is negative");
        }
    }

    public int getTimerId() {
        return timerId;
    }
}
