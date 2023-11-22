package fr.uge.poo.visitors.stp.ex1;

public final class StartTimerCmd implements STPCommand {

    private int  timerId;

    public StartTimerCmd(int timerId){
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
