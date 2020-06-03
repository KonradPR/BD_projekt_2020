package Handlers;

import Entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.Date;
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
    private static <T> List<T> getAllEntities(Class<T> type)throws Exception{
        final Session session = ourSessionFactory.openSession();
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> q = builder.createQuery(type);
            q.from(type);
            List<T>res = session.createQuery(q).getResultList();
            return res;
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
    public List<Patient> getAllPatientsCurrentlyOnMed(int medId)throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            Medicine med = getMedicineById(medId);
            if (med == null) return new ArrayList<>();
            java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
            Query q = session.createQuery("select p.patient from Prescription as p where exists " +
                    "(from PrescriptionElement as el where el.medicine.evidenceNumber = :medId and el.prescription = p) and " +
                    "p.expires >= :now").setParameter("medId",medId).setParameter("now",now);
            return q.getResultList();
        }finally {
            session.close();
        }
    }

    //Return list of patients that used to take given medicine
    public List<Patient> getAllPatientsThatUsedMed(int medId)throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            Medicine med = getMedicineById(medId);
            if (med == null) return new ArrayList<>();
            java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
            Query q = session.createQuery("select p.patient from Prescription as p where exists " +
                    "(from PrescriptionElement as el where el.medicine.evidenceNumber = :medId and el.prescription = p) and " +
                    "p.expires < :now").setParameter("medId",medId).setParameter("now",now);
            return q.getResultList();
        }finally {
            session.close();
        }
    }


    //Get list of all PrescriptionElements from given prescriptionID
    public List<PrescriptionElement> getPrescriptionElements(int prescriptionID) throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            Prescription prescription = getPrescriptionById(prescriptionID);
            if (prescription == null) return new ArrayList<>();
            Query q = session.createQuery("from PrescriptionElement as pe where " +
                    "pe.prescription.id = :prescriptionID").setParameter("prescriptionID",prescriptionID);
            return q.getResultList();
        }finally {
            session.close();
        }
    }

    //Get list of all suppliers that supply given medicine
    public List<Supplier> getMedicineSuppliers(int medId) throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            Medicine med = getMedicineById(medId);
            if (med == null) return new ArrayList<>();
            Query q = session.createQuery("select s from Supplier s join s.medicines m where " +
                    "m.id = :medId").setParameter("medId",medId);
            return q.getResultList();
        }finally {
            session.close();
        }
    }

    //Get list of all medicines supplied by given supplier
    public List<Medicine> getMedicinesFromSupplier(int supId) throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            Supplier supplier = getSupplierById(supId);
            if (supplier == null) return new ArrayList<>();
            Query q = session.createQuery("select m from Medicine m join m.suppliers s where " +
                    "s.id = :supId").setParameter("supId",supId);
            return q.getResultList();
        }finally {
            session.close();
        }
    }


}
