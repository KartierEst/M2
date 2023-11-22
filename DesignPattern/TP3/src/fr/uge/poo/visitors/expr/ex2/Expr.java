package fr.uge.poo.visitors.expr.ex2;

import java.util.Iterator;

public interface Expr {
    // public int eval();

    public static Expr parseExpr(Iterator<String> it) {
        if (!it.hasNext()) {
            throw new IllegalArgumentException("no more tokens");
        }
        String token = it.next();
        switch(token) {
            case "+":
                return new BinOp(parseExpr(it), parseExpr(it), token, (a, b) -> a + b);
            case "-":
                return new BinOp(parseExpr(it), parseExpr(it), token, (a, b) -> a - b);
            case "*":
                return new BinOp(parseExpr(it), parseExpr(it), token, (a, b) -> a * b);
            case "/":
                return new BinOp(parseExpr(it), parseExpr(it), token, (a, b) -> a / b);
            default:
                return new Value(Integer.parseInt(token));
        }
    }

    // on prend un type générique et on le donne au visiteur et en retour de methode
    // au lieu de int on peut avoir int et string par exemple
    //<T> T accept(ExprVisitor<T> visitor);
    //int accept(ExprVisitor visitor);

    <T, C> T accept(ExprVisitor<T,C> visitor, C context); // possible de donner le context
}
