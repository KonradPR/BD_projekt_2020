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

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "suppliers",cascade = CascadeType.PERSIST)
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

    public int getSupplierID() {
        return supplierID;
    }
    public String getCompanyName() {
        return companyName;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address.toString();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCity(String city) {
        this.address.setCity(city);
    }

    public void setStreet(String street) {
        this.address.setStreet(street);
    }

    public void setZipCode(String zipCode) {
        this.address.setZipCode(zipCode);
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setMedicines(Set<Medicine> medicines) {
        this.medicines = medicines;
    }
}

