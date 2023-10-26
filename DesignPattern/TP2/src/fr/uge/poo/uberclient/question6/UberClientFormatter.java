package fr.uge.poo.uberclient.question6;

public interface UberClientFormatter {
    String format(UberClientInfo info, int limit);

    String simpleFormat(UberClientInfo info);
}
