package fr.uge.poo.uberclient.question4;

import java.util.List;

public interface UberClientFormatter {
    String formatHtml(UberClientInfo info, int limit);

    String toHTMLSimple(UberClientInfo info);
}
