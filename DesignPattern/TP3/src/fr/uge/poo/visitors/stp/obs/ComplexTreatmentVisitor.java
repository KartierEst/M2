package fr.uge.poo.visitors.stp.obs;

import java.time.LocalDateTime;
import java.util.*;

public class ComplexTreatmentVisitor implements STPVisitor {

    HashMap<Integer,Long> timers = new HashMap<>();

    private final List<STPObserver> observers = new ArrayList<>();

    public void register(STPObserver... observer){
        Objects.requireNonNull(observer);
        observers.addAll(Arrays.asList(observer));
    }

    @Override
    public void visit(HelloCmd parser) {
        //System.out.println("Au revoir");
        observers.forEach(x -> x.start(parser));
        System.out.println("Hello the current date is "+ LocalDateTime.now());
    }

    @Override
    public void visit(StartTimerCmd parser) {
        //System.out.println("Pas compris");
        var timerId = parser.getTimerId();
        if (timers.get(timerId)!=null){
            System.out.println("Timer "+timerId+" was already started");
            return;
        }
        var currentTime =  System.currentTimeMillis();
        timers.put(timerId,currentTime);
        observers.forEach(x -> {x.start(parser); x.onStartChronos(parser);});
        System.out.println("Timer "+timerId+" started");

    }

    @Override
    public void visit(StopTimerCmd parser) {
        //System.out.println("Pas compris");
        var timerId = parser.getTimerId();
        var startTime = timers.get(timerId);
        if (startTime==null){
            System.out.println("Timer "+timerId+" was never started");
            return;
        }
        var currentTime = System.currentTimeMillis();
        observers.forEach(x -> {x.start(parser); x.onStopChronos(parser);});
        System.out.println("Timer "+timerId+" was stopped after running for "+(currentTime-startTime)+"ms");
        timers.put(timerId,null);
    }

    @Override
    public void visit(ElapsedTimeCmd parser) {
        //System.out.println("Pas compris");
        var currentTime =  System.currentTimeMillis();
        for(var timerId : parser.getTimers()){
            var startTime = timers.get(timerId);
            if (startTime == null){
                System.out.println("Unknown timer "+timerId);
                return;
            }
            observers.forEach(x -> x.start(parser));
            System.out.println("Ellapsed time on timerId "+timerId+" : "+(currentTime-startTime)+"ms");
        }
    }

    public void end(){
        observers.forEach(STPObserver::end);
    }
}
