package fr.uge.poo.uberclient.question5;

import java.util.List;

public record AverageLimitStrategy(int limit) implements ScoreStrategy {
    @Override
    public double computeScore(List<Integer> grades) {
        return grades.stream().limit(limit).mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
    }
}
