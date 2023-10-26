package fr.uge.poo.uberclient.question3;

import java.util.List;

public interface Step {
    // Interface Segregation Principle
    // trop d'interface pour peu de chose
    interface One {
        Two firstname(String firstname);
    }

    interface Two {
        Three lastname(String lastname);
    }

    interface Three {
        Four uid(long uid);
        Four randomUid();
    }

    interface Four {
        Five grade(int grade);
        Five grades(List<Integer> grades);
    }

    interface Five extends Four {
        Six email(String email);
        Six phoneNumber(String phoneNumber);
        // comme l'un des 2 est obligatoire (et pas les 2) on les réunis pour pas obligé d'avoir les 2
    }

    interface Six extends Five{
        UberClient build();
    }
}
