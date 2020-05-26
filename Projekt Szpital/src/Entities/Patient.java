package Entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PatientID") private int patientID;

    @Column(name = "Name") private String name;
    @Column(name = "Surname") private String surname;
    @Column(name = "DateOfBirth") private Date dateOfBirth;
    @Column(name = "Gender") private String gender;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name="Patient")
    private Set<Prescription> prescriptions = new HashSet<>();

    public Patient(){}

    public Patient(String name, String surname, Date dateOfBirth, String gender){
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public String toString(){
        return patientID+" "+name+" "+surname+" "+dateOfBirth.toString()+" "+gender;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    public void addPrescription(Prescription prescription){
        prescriptions.add(prescription);
    }

    //Setters
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setName(String name) {
        this.name = name;
    }
}
