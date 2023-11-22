package fr.uge.poo.visitors.expr.ex2;

import java.util.Iterator;
import java.util.regex.Pattern;

public class Application {
    public static void main(String[] args) {
        Iterator<String> it = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
        Expr expr = Expr.parseExpr(it);
        var evalVisitor = new EvalExprVisitor();
        var stringVisitor = new ToStringVisitor();
        var stringBuilder = new StringBuilder();
        //System.out.println(expr);
        System.out.println(expr.accept(evalVisitor, null));
        //System.out.println(expr.accept(stringVisitor));
        System.out.println(expr.accept(stringVisitor,stringBuilder));
        System.out.println(stringBuilder);
    }
}
