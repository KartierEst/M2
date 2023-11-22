package fr.uge.poo.visitors.stp.stphipster;

import java.util.List;
import java.util.Objects;

public record ElapsedTimeCmd(List<Integer> timers) implements STPCommand {


    public ElapsedTimeCmd {
        Objects.requireNonNull(timers);
    }

    public List<Integer> getTimers() {
        return timers;
    }
}
