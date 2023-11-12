package fr.uge.poo.uberclient.question6;

import java.util.List;

public interface UberClientFormatter {
    default String format(UberClientInfo info) {return "";};

    default String format(UberClientInfo info, double average){return "";}

    default List<String> privateMails(List<String> emails) {
        return emails.stream().map(x -> {
            var tab = x.split("@");
            return tab[0].charAt(0) + "*@" + tab[1].charAt(0) + "*";
        }).toList();
    }

    class HTMLWithAverage implements UberClientFormatter {
        // aussi avec overlast 7
        @Override
        public String format(UberClientInfo info, double average) {
            return String.format("<h2>%s %s  (%1.2f*)</h2>",info.firstname(),info.lastname(), average);
        }
    }

    class HTMLSimple implements UberClientFormatter {
        @Override
        public String format(UberClientInfo info) {
            return String.format("<h2>%s %s </h2>",info.firstname(),info.lastname());
        }
    }

    class HTMLWithEmails implements UberClientFormatter {
        // avec aussi overlast5 grades
        @Override
        public String format(UberClientInfo info, double average) {
            return String.format("<h2>%s %s (%1.2f*) : %s </h2>",info.firstname(),info.lastname(),average,privateMails(info.emails()));
        }
    }
}
