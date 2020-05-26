package Handlers;

import Entities.Doctor;
import Entities.Medicine;
import Entities.Patient;
import Entities.Supplier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    public Doctor getDoctorById(int id) throws Exception{
        return getEntityById(Doctor.class,id);
    }
    public Medicine getMedicineById(int id) throws Exception{
        return getEntityById(Medicine.class,id);
    }
    public Patient getPatientById(int id) throws Exception{
        return getEntityById(Patient.class,id);
    }
    public Supplier getSuppleirById(int id)throws Exception{
        return getEntityById(Supplier.class,id);
    }

    //Return list of patients that are currently taking given medicine
    public List<Patient> getAllPatienCurrentlyOnMed(int medId)throws Exception{
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
}
