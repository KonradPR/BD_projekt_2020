package Handlers;

import Entities.*;
import LogicUtils.Parser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.DAYS;

public class TransactionHandler {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void addDoctor(String name, String surname, Date dateOfBirth, String phone, String email, String speciality) throws Exception {
        final Session session = ourSessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Doctor doc = new Doctor(name, surname, dateOfBirth, phone, email, speciality);
            session.save(doc);
            tx.commit();
        } finally {
            session.close();
        }
    }

    public void addPatient(String name, String surname, Date dateOfBirth, String gender) throws Exception {
        final Session session = ourSessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Patient p = new Patient(name, surname, dateOfBirth,gender);
            session.save(p);
            tx.commit();
        } finally {
            session.close();
        }
    }

    public void addSupplier(String companyName, String phone, String street, String city, String zipCode) throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Supplier s = new Supplier(companyName,phone,street,city,zipCode);
            session.save(s);
            tx.commit();
        } finally {
            session.close();
        }
    }

    public void addMedicine(String suggestedDose,int inStock) throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Medicine m = new Medicine(suggestedDose,inStock);
            session.save(m);
            tx.commit();
        } finally {
            session.close();
        }
    }
    //Creates connection between given supplier and medicine
    public void addMedicineSupplier(Medicine m,Supplier s){
        final Session session = ourSessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            m.addSupplier(s);
            s.addMedicine(m);
            session.update(s);
            session.update(m);
            tx.commit();
        } finally {
            session.close();
        }
    }

    //This method return true if there are enough dosages in stock to fill prescription;
    private boolean isEnoughMedicineInStock(Date start,Date end, int medId, String Dosage)throws Exception{
        final Session session = ourSessionFactory.openSession();
        try{
            Medicine m = session.get(Medicine.class,medId);
            if(Parser.parseDosageUnit(m.getSuggestedDose())==Parser.parseDosageUnit(Dosage)) throw new IllegalArgumentException("Dosage type is different than in stock units");
            int days = (int) TimeUnit.DAYS.convert(start.getTime()-end.getTime(), TimeUnit.MILLISECONDS);
            int neededDosages = days * Parser.parseDosageValue(Dosage);
            int avaliableDosages = m.getInStock() * Parser.parseDosageValue(m.getSuggestedDose());
            if(neededDosages>avaliableDosages)return false;
            return true;

        }finally{
            session.close();
        }
    }

    //This meethod returns number of dosages left after filling prescription
    private int medicineDosagesLeftAfterFill(Date start,Date end,String suggested,int inStock,String Dosage) throws Exception{
        final Session session = ourSessionFactory.openSession();
        try{
            int days = (int) TimeUnit.DAYS.convert(start.getTime()-end.getTime(), TimeUnit.MILLISECONDS);
            int neededDosages = days * Parser.parseDosageValue(Dosage);
            int avaliableDosages = inStock * Parser.parseDosageValue(suggested);
            return avaliableDosages - neededDosages;
        }finally{
            session.close();
        }
    }

    //Creates new prescirption with given dates assigns it to given patient and doctor
    // also creates neededPrescriptionElemts all done in single transaction
    public void addPrescription(Date given, Date expires, int doctorId, int patientId, List<Medicine> meds,List<String>dosages) throws Exception{
        if(meds.size()!=dosages.size()||meds.size()==0) throw new IllegalArgumentException("Presciption cannot be empty, and all meds need dosage");
        final Session session = ourSessionFactory.openSession();
        try{
            Transaction tx = session.beginTransaction();
            Prescription p = new Prescription(given,expires);
            List<PrescriptionElement> elems = new ArrayList<>();
            for(int i=0;i<meds.size();i++){
                PrescriptionElement tmp = new PrescriptionElement(dosages.get(i),p,meds.get(i));
                elems.add(tmp);
                p.addPrescriptionElement(tmp);
            }
            Doctor d = session.get(Doctor.class,doctorId);
            Patient t = session.get(Patient.class,patientId);
            d.addPrescription(p);
            t.addPrescription(p);
            session.update(d);
            session.update(t);
            session.save(p);
            for(PrescriptionElement tmp : elems){
                session.save(tmp);
            }
            tx.commit();
        }finally {
            session.close();
        }
    }




}
