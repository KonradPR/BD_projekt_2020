package Handlers;

import Entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class DataAccessHandler {
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

    //templates
    private <T> List<T> getAllEntities(Class<T> type)throws Exception{
        final Session session = ourSessionFactory.openSession();
        try{
            return session.createQuery(session.getCriteriaBuilder().createQuery(type)).getResultList();
        }finally{
            session.close();
        }
    }
    private <T>  T getEntityById(Class<T> type,int id) throws Exception{
        final Session session = ourSessionFactory.openSession();
        try{
            return session.get(type,id);
        }finally{
            session.close();
        }
    }

    //getAll methods
    public List<Doctor> getAllDoctors() throws Exception{
        return getAllEntities(Doctor.class);
    }
    public List<Patient> getAllPatients() throws Exception{
        return getAllEntities(Patient.class);
    }
    public List<Supplier> getAllSuppliers() throws Exception{
        return getAllEntities(Supplier.class);
    }
    public List<Medicine> getAllMedicines() throws Exception{
        return getAllEntities(Medicine.class);
    }
    public List<Prescription> getAllPrescriptions() throws Exception{
        return getAllEntities(Prescription.class);
    }

    //getByID methods
    public Doctor getDoctorById(int id) throws Exception{
        return getEntityById(Doctor.class,id);
    }
    public Medicine getMedicineById(int id) throws Exception{
        return getEntityById(Medicine.class,id);
    }
    public Patient getPatientById(int id) throws Exception{
        return getEntityById(Patient.class,id);
    }
    public Supplier getSupplierById(int id)throws Exception{
        return getEntityById(Supplier.class,id);
    }
    public Prescription getPrescriptionById(int id) throws Exception{
        return getEntityById(Prescription.class,id);
    }

    //Return list of patients that are currently taking given medicine
    public List<Patient> getAllPatientCurrentlyOnMed(int medId)throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            Medicine med = getMedicineById(medId);
            if (med == null) return new ArrayList<>();
            Query q = session.createQuery("from Patient left outer join Prescription" +
                    " on Prescription in elements(Patient.prescriptions)" +
                    " left outer join PrescriptionElement  " +
                    "on Prescription.prescriptionNumber = PrescriptionElement.prescription.prescriptionNumber" +
                    " left outer join Medicine on Medicine.evidenceNumber = PrescriptionElement.medicine.evidenceNumber where Medicine.evidenceNumber" +
                    " = :medId ");
            return q.getResultList();
        }finally {
            session.close();
        }
    }


    //Get list of all medicines from given prescriptionID
    public List<PrescriptionElement> getPrescriptionElements(int prescriptionID) throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            Prescription prescription = getPrescriptionById(prescriptionID);
            if (prescription == null) return new ArrayList<>();
            Query q = session.createQuery("from PrescriptionElement left outer join Prescription " +
                    " on PrescriptionElement in elements(Prescription.prescriptionElements)" +
                    "where Prescription.id = :prescriptionID");
            return q.getResultList();
        }finally {
            session.close();
        }
    }

    public List<PrescriptionElement> getMedicineSuppliers(int medId) throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            Medicine med = getMedicineById(medId);
            if (med == null) return new ArrayList<>();
            Query q = session.createQuery("from Supplier join Medicine " +
                    "on Medicine in elements(Supplier.medicines)" +
                    "where Medicine.id = :medId");
            return q.getResultList();
        }finally {
            session.close();
        }
    }

    public List<PrescriptionElement> getMedicinesFromSupplier(int supId) throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            Supplier supplier = getSupplierById(supId);
            if (supplier == null) return new ArrayList<>();
            Query q = session.createQuery("from Medicine join Supplier " +
                    "on Medicine in elements(Supplier.medicines)" +
                    "where Supplier.id = :supId");
            return q.getResultList();
        }finally {
            session.close();
        }
    }


}
