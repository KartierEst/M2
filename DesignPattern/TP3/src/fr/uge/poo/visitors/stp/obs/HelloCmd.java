package fr.uge.poo.visitors.stp.obs;

public final class HelloCmd implements STPCommand {
    @Override
    public void accept(STPVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "HelloCmd";
    }
}
