package fr.uge.poo.visitors.stp.stphipster;

public record StopTimerCmd(int timerId) implements STPCommand {


    public StopTimerCmd{
        if(timerId < 0){
            throw new IllegalArgumentException("timer is negative");
        }
    }

    public int getTimerId() {
        return timerId;
    }

}
