package fr.uge.jee.onlineshop;

public class ReturnInsurance implements Insurance{
    private final boolean forMembers;

    public ReturnInsurance() {
        this.forMembers = false;
    }
    public ReturnInsurance(boolean forMembers) {
        this.forMembers = forMembers;
    }

    @Override
    public String description() {
        return forMembers ? "Return insurance only for members" : "Return insurance";
    }
}
