package fr.uge.poo.uberclient.question6;

import java.util.List;

public interface ScoreStrategy {
    double computeScore(List<Integer> grades);

    class AverageStrategy implements ScoreStrategy {
        @Override
        public double computeScore(List<Integer> grades) {
            return grades.stream().mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
        }
    }

    record AverageLimitStrategy(int limit) implements ScoreStrategy {
        public AverageLimitStrategy{
            if(limit < 0){
                throw new IllegalArgumentException("limit is negative");
            }
        }
        @Override
        public double computeScore(List<Integer> grades) {
            return grades.stream().limit(limit).mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
        }
    }
}


