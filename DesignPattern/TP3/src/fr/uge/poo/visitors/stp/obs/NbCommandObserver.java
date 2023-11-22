package fr.uge.poo.visitors.stp.obs;

import java.util.HashMap;

public class NbCommandObserver implements STPObserver{
    private final HashMap<String, Integer> hashMap = new HashMap<>();
    @Override
    public void start(STPCommand command) {
        hashMap.merge(command.toString(), 1, Integer::sum);
    }

    @Override
    public void end() {
        hashMap.forEach((k,v) -> System.out.println("Nombre de " + k + ": " + v));
    }
}
