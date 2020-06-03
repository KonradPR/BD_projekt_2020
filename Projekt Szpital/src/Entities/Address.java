package Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(name = "Street") private String street;
    @Column(name = "City") private String city;
    @Column(name = "ZipCode") private String zipCode;

    public Address(){}

    public Address(String street,String city,String zipCode){
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return city + " " + street + " " +zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
