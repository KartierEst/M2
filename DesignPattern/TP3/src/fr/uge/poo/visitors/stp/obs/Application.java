package fr.uge.poo.visitors.stp.obs;

import java.util.Optional;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        var visitor = new ComplexTreatmentVisitor();
        var nbCommandObserver = new NbCommandObserver();
        var avgObserver = new AvgChronosObserver();
        visitor.register(nbCommandObserver, avgObserver);
        //visitor.register(avgObserver);
        var scan = new Scanner(System.in);
        while(scan.hasNextLine()){
            var line = scan.nextLine();
            if (line.equals("quit")){
                break;
            }
            Optional<STPCommand> answer = STPParser.parse(line);
            if (answer.isEmpty()){
                System.out.println("Unrecognized command");
                continue;
            }
            STPCommand cmd = answer.get();
            cmd.accept(visitor);
        }
        visitor.end();
    }
}
