package fr.uge.jee.hibernate.studentsbi.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Embeddable
public class Address {

    @Id
    @GeneratedValue
    private long id;

    private int streetNumber;
    private String street;

    public Address(long id, int streetNumber, String street) {
        this.id = id;
        this.streetNumber = streetNumber;
        this.street = street;
    }

    public Address() {}

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
