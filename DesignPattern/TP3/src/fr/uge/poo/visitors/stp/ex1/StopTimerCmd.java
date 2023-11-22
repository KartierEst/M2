package fr.uge.poo.visitors.stp.ex1;

public final class StopTimerCmd implements STPCommand {

    private int timerId;

    public StopTimerCmd(int timerId){
        this.timerId=timerId;
    }

    public int getTimerId() {
        return timerId;
    }

    @Override
    public void accept(STPVisitor visitor) {
        visitor.visit(this);
    }
}
