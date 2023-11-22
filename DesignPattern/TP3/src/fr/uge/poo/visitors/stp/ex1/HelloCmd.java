package fr.uge.poo.visitors.stp.ex1;

public final class HelloCmd implements STPCommand {
    @Override
    public void accept(STPVisitor visitor) {
        visitor.visit(this);
    }
}
