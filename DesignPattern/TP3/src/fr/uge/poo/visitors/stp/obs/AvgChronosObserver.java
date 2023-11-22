package fr.uge.poo.visitors.stp.obs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AvgChronosObserver implements STPObserver {

    private final HashMap<Integer, Long> hashMap =  new HashMap<>();
    private final List<Long> durations = new ArrayList<>();

    @Override
    public void onStartChronos(StartTimerCmd cmd) {
        try {
            hashMap.put(cmd.getTimerId(), System.currentTimeMillis());
        } catch(NullPointerException e){
            throw new IllegalStateException("Timer with this id " + cmd.getTimerId() + " already exist");
        }
    }

    @Override
    public void onStopChronos(StopTimerCmd cmd) {
        try {
            durations.add(System.currentTimeMillis() - hashMap.get(cmd.getTimerId()));
        } catch(NullPointerException e){
            throw new IllegalStateException("No timer with this id " + cmd.getTimerId() + " exist");
        }
    }

    @Override
    public void end() {
        if(durations.isEmpty()){
            System.out.println("Aucun timer a été lancé");
            return;
        }
        var avg = durations.stream().mapToLong(Long::longValue).average();
        if(avg.isPresent()) {
            System.out.println("la durée moyenne est de " + avg.getAsDouble() + "ms");
        }
    }
}
