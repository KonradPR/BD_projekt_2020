package Entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EmployeeID") private int employeeID;

    @Column(name = "Name") private String name;
    @Column(name = "Surname") private String surname;
    @Column(name = "DateOfBirth") private Date dateOfBirth;
    @Column(name = "Phone") private String phone;
    @Column(name = "Email") private String email;
    @Column(name = "Speciality") private String speciality;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name="Doctor")
    private Set<Prescription> prescriptions = new HashSet<>();

    public Doctor(){}

    public Doctor(String name, String surname, Date dateOfBirth, String phone, String email,String speciality){
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.speciality = speciality;
    }

    public String toString(){
        return employeeID+" "+name+" "+surname+" "+phone+" "+email+" "+speciality;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    //Setters
    public void addPrescription(Prescription prescription){
        this.prescriptions.add(prescription);
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
