package fr.uge.jee.annotations.onlineshope;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReturnInsurance implements Insurance{
    private final boolean forMembers;

    public ReturnInsurance() {
        this.forMembers = false;
    }
    public ReturnInsurance(@Value("${onlineshop.returninsurance.membersonly}") boolean forMembers) {
        this.forMembers = forMembers;
    }

    @Override
    public String description() {
        return forMembers ? "Return insurance only for members" : "Return insurance";
    }
}
