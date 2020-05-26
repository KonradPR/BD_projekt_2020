package Entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SupplierID") private int supplierID;

    @Column(name = "CompanyName") private String companyName;
    @Column(name = "Phone") private String phone;
    @Embedded private Address address;

    @ManyToMany(mappedBy = "suppliers",cascade = CascadeType.PERSIST)
    Set<Medicine> medicines = new HashSet<>();

    public Supplier(){}

    public Supplier(String companyName, String phone, String street, String city, String zipCode){
        this.companyName = companyName;
        this.phone = phone;
        this.address = new Address(street,city,zipCode);
    }

    public String toString(){return supplierID+" "+companyName+" "+phone+" "+address;}

    public void addMedicine(Medicine medicine){
        medicines.add(medicine);
    }

}

