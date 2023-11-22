package fr.uge.poo.visitors.expr.ex3;

import java.util.regex.Pattern;


public class Application {
    public static void main(String[] args) {
        var it = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
        var expr = Expr.parseExpr(it);

        var evaluator = new ExprVisitor<Integer, Object>();
        evaluator
                .when(BinOp.class, (binOp, context) -> binOp
                        .getOperator()
                        .applyAsInt(
                                evaluator.visit(binOp.getLeft(), null),
                                evaluator.visit(binOp.getRight(), null)
                        )
                )
                .when(Value.class, (value, context) -> value.getValue());
        System.out.println(evaluator.visit(expr, null));

        var toString = new ExprVisitor<Object, StringBuilder>();
        toString
                .when(BinOp.class, (binOp, context) -> {
                    context.append('(');
                    toString.visit(binOp.getLeft(), context);
                    context.append(' ').append(binOp.getOpName()).append(' ');
                    toString.visit(binOp.getRight(), context);
                    context.append(')');
                    return null;
                })
                .when(Value.class, (value, context) -> {
                    context.append(value.getValue());
                    return null;
                });
        var sb = new StringBuilder();
        toString.visit(expr, sb);
        System.out.println(sb);
    }
}
