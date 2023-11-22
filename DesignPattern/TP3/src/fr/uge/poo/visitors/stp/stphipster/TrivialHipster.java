package fr.uge.poo.visitors.stp.stphipster;


import java.util.Optional;
import java.util.Scanner;

public class TrivialHipster {
    public static void main(String[] args) {
        var visitor = new ComplexTreatmentVisitor();
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
            visitor.accept(cmd);
        }
    }
}


