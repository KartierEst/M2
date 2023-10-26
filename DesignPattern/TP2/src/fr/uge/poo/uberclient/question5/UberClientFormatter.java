package fr.uge.poo.uberclient.question5;

public interface UberClientFormatter {
    String formatHtml(UberClientInfo info, int limit);

    String toHTMLSimple(UberClientInfo info);
}
