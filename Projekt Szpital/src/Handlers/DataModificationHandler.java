package Handlers;

import Entities.Doctor;
import Entities.Patient;
import LogicUtils.Parser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DataModificationHandler {
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

    public void modifyDoctorById(int id,String email,String name,String phone,String speciality,String surname) throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            DataAccessHandler da = new DataAccessHandler();
            Transaction tx = session.beginTransaction();

            Doctor doctor = da.getDoctorById(id);
            if(doctor != null) {
                if (email != null){
                    if(!Parser.isValidEmail(email)) throw new IllegalArgumentException("Email is invalid");
                    doctor.setEmail(email);
                }
                if (name != null){
                    if(!Parser.isValidName(name)) throw new IllegalArgumentException("Name is Invalid");
                    doctor.setName(name);
                }
                if (phone != null){
                    if(!Parser.isValidPhone(phone))throw new IllegalArgumentException("Phone is invalid");
                    doctor.setPhone(phone);
                }
                if (speciality != null) doctor.setSpeciality(speciality);
                if (surname != null){
                    if(!Parser.isValidSurname(surname))throw  new IllegalArgumentException("Surname is invalid");
                    doctor.setSurname(surname);
                }
                session.update(doctor);
                tx.commit();
            }
        }finally {
            session.close();
        }
    }

    public void modifyPatientById(int id,String name,String surname) throws Exception{
        final Session session = ourSessionFactory.openSession();
        try {
            DataAccessHandler da = new DataAccessHandler();
            Transaction tx = session.beginTransaction();

            Patient patient = da.getPatientById(id);
            if(patient != null) {
                if (name != null) {
                    if(!Parser.isValidName(name)) throw new IllegalArgumentException("Name is Invalid");
                    patient.setName(name);
                }
                if (surname != null) {
                    if(!Parser.isValidSurname(surname))throw  new IllegalArgumentException("Surname is invalid");
                    patient.setSurname(surname);
                }
                session.update(patient);
                tx.commit();
            }
        }finally {
            session.close();
        }
    }
}
