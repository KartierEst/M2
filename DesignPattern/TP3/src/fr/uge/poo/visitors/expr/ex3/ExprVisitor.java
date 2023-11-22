package fr.uge.poo.visitors.expr.ex3;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiFunction;

public class ExprVisitor<T,C> {
    // hashmap qui prend la classe et une bi fonction qui prend en param une des classe et le stringbuilder
    // et renvoit le type qu'on veut (string ou int)
    private final HashMap<Class<? extends Expr>, BiFunction<Expr, C, T>> applications = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <R extends Expr> ExprVisitor<T, C> when(Class<R> type, BiFunction<R, C, T> application) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(application);
        applications.put(type, (BiFunction<Expr, C, T>) application);
        return this;
    }

    public T visit(Expr expr, C context) {
        return applications.get(expr.getClass()).apply(expr, context);
    }
}
