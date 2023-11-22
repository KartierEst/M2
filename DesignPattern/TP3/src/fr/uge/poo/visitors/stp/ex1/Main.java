package fr.uge.poo.visitors.stp.ex1;

import java.util.Scanner;

public class Main {
    // open close principle
    // on pourrait créer une méthode dans l'interface STPCommand
    // pour utiliser le polymorphisme et faire pour chacune des commandes
    // son utilisation mais le problème serait que l'on est trop de responsabilités
    // si on ajoute d'autres méthodes à la suite
    // pas de single responsibility et interface segregation
    // On va donc utiliser le Visitor
    // un traitement xomplexe par classe
    public static void main(String[] args) {
        var visitor = new ComplexTreatmentVisitor();
        try(var scanner = new Scanner(System.in)){
            while(true) {
                var result = STPParser.parse(scanner.nextLine());
                if(result.isPresent()){
                    result.get().accept(visitor);
                }
                else{
                    System.out.println("Non implementé");
                }
            }
        }
    }
}
