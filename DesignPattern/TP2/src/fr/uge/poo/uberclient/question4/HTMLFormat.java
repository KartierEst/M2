package fr.uge.poo.uberclient.question4;

import java.util.List;

public class HTMLFormat implements UberClientFormatter {

    @Override
    public String formatHtml(UberClientInfo info, int limit){
        if(limit == 0 && info.emails() == null){
            return toHTML(info);
        }
        else if(limit > 0 && info.emails() == null){
            return toHTMWithAverageOverLast7Grades(info);
        }
        else if(limit == 0 && info.emails() != null){
            return toHtmlWithEmails(info);
        }
        return toHtmlWithEmailsAndAverageOverLast5Grades(info);
    }

    public String toHTML(UberClientInfo info) {
        //var averageGrade= grades.stream().mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
        return String.format("<h2>%s %s  (%1.2f*)</h2>",info.firstname(),info.lastname(),info.averageGrade());
    }


    public String toHTMWithAverageOverLast7Grades(UberClientInfo info) {
        return toHTML(info);
    }

    @Override
    public String toHTMLSimple(UberClientInfo info) {
        return String.format("<h2>%s %s </h2>",info.firstname(),info.lastname());
    }


    public String toHtmlWithEmails(UberClientInfo info) {
        //var averageGrade = grades.stream().mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
        return String.format("<h2>%s %s (%1.2f*) : %s </h2>",info.firstname(),info.lastname(),info.averageGrade(),info.emails());
    }


    public String toHtmlWithEmailsAndAverageOverLast5Grades(UberClientInfo info) {
        //var averageGrade= grades.stream().limit(5).mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
        return String.format("<h2>%s %s (%1.2f*) : %s </h2>",info.firstname(),
                info.lastname(),info.averageGrade(),info.emails());
    }

}
