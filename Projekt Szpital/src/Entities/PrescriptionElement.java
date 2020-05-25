package Entities;

import javax.persistence.*;

@Entity
public class PrescriptionElement {
    @EmbeddedId
    private PrescriptionElementID ID;

    @ManyToOne
    @MapsId("prescriptionID")
    private Prescription prescription;

    @ManyToOne
    @MapsId("evidenceID")
    private Medicine medicine;

    @Column(name = "Dose") private String dose;

    public PrescriptionElement(){}

    public PrescriptionElement(String dose, Prescription prescription,Medicine medicine){
        this.dose = dose;
        this.prescription = prescription;
        this.medicine = medicine;
        this.ID = new PrescriptionElementID(prescription.getId(),medicine.getId());
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

}
