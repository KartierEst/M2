package fr.uge.poo.visitors.stp.ex1;

import java.util.List;
import java.util.Objects;

public final class ElapsedTimeCmd implements STPCommand {

    private final List<Integer> timers;

    public ElapsedTimeCmd(List<Integer> timers) {
        Objects.requireNonNull(timers);
        this.timers = List.copyOf(timers);
    }

    public List<Integer> getTimers() {
        return timers;
    }

    @Override
    public void accept(STPVisitor visitor) {
        visitor.visit(this);
    }
}
