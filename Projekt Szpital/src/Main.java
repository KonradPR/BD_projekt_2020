import Entities.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.sql.Date;

public class Main {
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

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            Transaction tx = session.beginTransaction();

            //testy czy wszystko ładnie wchodzi do bazy
            Doctor doctor = new Doctor("Adam","Krasny",Date.valueOf("1993-12-30") ,
                    "609 123 321","adam.lekarz@op.pl","Dermatolog");

            Patient patient = new Patient("Mariusz","Kowalski",Date.valueOf("1999-11-30"),"M");
            Supplier supplier1 = new Supplier("Firma","989 321 345","Krakow");
            Supplier supplier2 = new Supplier("Inna Firma","123 321 345","Warszawa");

            Medicine medicine1 = new Medicine("2x200ml",3);
            Medicine medicine2 = new Medicine("1 tabletka",2);

            Prescription prescription = new Prescription(Date.valueOf("2019-11-30"),Date.valueOf("2020-01-30"));

            doctor.addPrescription(prescription);
            patient.addPrescription(prescription);

            PrescriptionElement prescriptionElement1 = new PrescriptionElement("100ml",prescription,medicine1);
            PrescriptionElement prescriptionElement2 = new PrescriptionElement("2 tabletki",prescription,medicine2);

            supplier1.addMedicine(medicine1);
            supplier2.addMedicine(medicine2);
            medicine1.addSupplier(supplier1);
            medicine2.addSupplier(supplier2);

            session.save(supplier1);
            session.save(supplier2);
            session.save(medicine1);
            session.save(medicine2);
            session.save(doctor);
            session.save(patient);
            session.save(prescription);
            session.save(prescriptionElement1);
            session.save(prescriptionElement2);

            System.out.println("Changes should be done");
            tx.commit();
        } finally {
            session.close();
        }
    }
}