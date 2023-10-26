package fr.uge.poo.uberclient.question5;

import java.util.List;

public class AverageStrategy implements ScoreStrategy{
    @Override
    public double computeScore(List<Integer> grades) {
        return grades.stream().mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
    }
}
