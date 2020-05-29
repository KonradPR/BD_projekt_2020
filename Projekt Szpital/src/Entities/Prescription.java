package Entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PrescriptionNumber") private int prescriptionNumber;

    @Column(name = "Given")private Date given;
    @Column(name = "Expires")private Date expires;

    @ManyToOne
    @JoinColumn(name = "Doctor")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "Patient")
    private Patient patient;

    @OneToMany(mappedBy = "prescription",cascade = CascadeType.PERSIST)
    private Set<PrescriptionElement> prescriptionElements = new HashSet<>();

    public Prescription(){}

    public Prescription(Date given, Date expires){
        this.given = given;
        this.expires = expires;
    }

    public void addPrescriptionElement(PrescriptionElement prescriptionElement){
        prescriptionElements.add(prescriptionElement);
    }

    public int getId() {
        return prescriptionNumber;
    }

    @Override
    public String toString() {
        return prescriptionNumber + " " + given + "-" + expires + " " + doctor.getFullName() + " " + patient.getFullName();
    }

    public int getPrescriptionNumber() {
        return prescriptionNumber;
    }
    public Date getExpires() {
        return expires;
    }
    public Date getGiven() {
        return given;
    }
}
