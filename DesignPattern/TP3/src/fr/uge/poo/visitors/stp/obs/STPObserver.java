package fr.uge.poo.visitors.stp.obs;
public interface STPObserver {
    default void start(STPCommand command){
    }

    default void end(){
    }

    default void onStartChronos(StartTimerCmd cmd){
    }

    default void onStopChronos(StopTimerCmd cmd){
    }
}
