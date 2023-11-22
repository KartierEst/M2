package fr.uge.poo.visitors.expr.ex2;

public class EvalExprVisitor implements /*ExprVisitor ExprVisitor<Integer>*/ ExprVisitor<Integer, Object>  {

    @Override
    public Integer visitValue(Value value, Object context) {
        return value.getValue();
    }

    @Override
    public Integer visitBinOp(BinOp binOp, Object context) {
        return binOp.getOperator().applyAsInt(binOp.getLeft().accept(this, null), binOp.getRight().accept(this, null));
    }

/*    @Override
    public Integer visitValue(Value value) {
        return value.getValue();
    }

    @Override
    public Integer visitBinOp(BinOp binOp) {
        return binOp.getOperator().applyAsInt(binOp.getLeft().accept(this), binOp.getRight().accept(this));
    }*/

/*    @Override
    public int visitValue(Value value) {
        return value.getValue();
    }

    @Override
    public int visitBinOp(BinOp binOp) {
        return binOp.getOperator().applyAsInt(binOp.getLeft().accept(this), binOp.getRight().accept(this));
    }*/
}
