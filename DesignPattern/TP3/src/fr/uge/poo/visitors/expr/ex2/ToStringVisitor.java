package fr.uge.poo.visitors.expr.ex2;

public class ToStringVisitor implements /*ExprVisitor<String>*/ ExprVisitor<String, StringBuilder>{
/*    @Override
    public String visitValue(Value value) {
        return Integer.toString(value.getValue());
    }

    @Override
    public String visitBinOp(BinOp binOp) {
        return "(" + binOp.getLeft().accept(this) + ' ' + binOp.getOpName() + ' ' + binOp.getRight().accept(this) + ')';
    }*/

    @Override
    public String visitValue(Value value, StringBuilder context) {
        context.append(value.getValue());
        return null;
    }

    @Override
    public String visitBinOp(BinOp binOp, StringBuilder context) {
        context.append('(');
        binOp.getLeft().accept(this, context);
        context.append(' ').append(binOp.getOpName()).append(' ');
        binOp.getRight().accept(this, context);
        context.append(')');
        return null;
    }
}
