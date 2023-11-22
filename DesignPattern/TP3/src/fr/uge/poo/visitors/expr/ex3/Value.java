package fr.uge.poo.visitors.expr.ex3;

public class Value implements Expr {
    private final int value;

    public Value(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    @Override
    public String toString() {
        return Integer.toString(value);
    }


}
