package Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PrescriptionElementID implements Serializable {
    @Column(name = "PrescriptionNumber") private int prescriptionID;
    @Column(name = "EvidenceNumber") private int evidenceID;

    public PrescriptionElementID(int prescriptionID, int evidenceID){
        this.prescriptionID = prescriptionID;
        this.evidenceID = evidenceID;
    }

    public int getEvidenceID() {
        return evidenceID;
    }

    public int getPrescriptionID() {
        return prescriptionID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PrescriptionElementID other = (PrescriptionElementID) o;
        return Objects.equals(prescriptionID, other.prescriptionID) &&
                Objects.equals(evidenceID, other.evidenceID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prescriptionID, evidenceID);
    }
}
