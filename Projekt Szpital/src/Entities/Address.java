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
}
