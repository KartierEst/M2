package fr.uge.poo.uberclient.question5;

import java.util.List;

record UberClientInfo(String firstname, String lastname, double averageGrade, List<String> emails){
    UberClientInfo(String firstname, String lastname){
        this(firstname,lastname,-1,null);
    }
}
