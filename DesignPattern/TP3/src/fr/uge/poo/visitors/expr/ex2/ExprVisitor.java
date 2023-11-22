package fr.uge.poo.visitors.expr.ex2;

public interface ExprVisitor<T, C>/*ExprVisitor<T> ExprVisitor */{
/*    int visitValue(Value value);
    int visitBinOp(BinOp binOp);*/
/*    T visitValue(Value value);
    T visitBinOp(BinOp binOp);*/

    T visitValue(Value value, C context);
    T visitBinOp(BinOp binOp, C context);

}
