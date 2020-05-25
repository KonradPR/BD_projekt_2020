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
    @Column(name = "Address") private String address;

    @ManyToMany(mappedBy = "suppliers",cascade = CascadeType.PERSIST)
    Set<Medicine> medicines = new HashSet<>();

    public Supplier(){}

    public Supplier(String companyName, String phone, String address){
        this.companyName = companyName;
        this.phone = phone;
        this.address = address;
    }

    //TODO dodawanie po obydwuch stronach relacji bo teraz trzeba dodawać ręcznie
    public void addMedicine(Medicine medicine){
        medicines.add(medicine);
    }

}
