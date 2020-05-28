package Entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EvidenceNumber") private int evidenceNumber;

    @Column(name = "SuggestedDose") private String suggestedDose;
    @Column(name = "InStock") private int inStock;

    @OneToMany(mappedBy = "medicine",cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<PrescriptionElement> prescriptionElements = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Set<Supplier> suppliers = new HashSet<>();;

    public Medicine(){}

    public Medicine(String suggestedDose,int inStock){
        this.suggestedDose = suggestedDose;
        this.inStock = inStock;
        this.prescriptionElements = new HashSet<>();
    }

    public void addPrescriptionElement(PrescriptionElement prescriptionElement){
        prescriptionElements.add(prescriptionElement);
    }
    public String getSuggestedDose(){
        return this.suggestedDose;
    }

    public int getInStock() {
        return inStock;
    }

    public String toString(){return evidenceNumber+" "+suggestedDose+" "+inStock;}

    public void addSupplier(Supplier supplier){
        suppliers.add(supplier);
    }

    public int getId() {
        return evidenceNumber;
    }
}
