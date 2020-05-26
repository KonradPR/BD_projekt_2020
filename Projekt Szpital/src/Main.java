import Entities.*;
import Handlers.DataAccessHandler;
import Handlers.DataModificationHandler;
import Handlers.TransactionHandler;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
        try{
            TransactionHandler t = new TransactionHandler();
            DataAccessHandler d = new DataAccessHandler();
            DataModificationHandler dm = new DataModificationHandler();

//            t.addDoctor("Karl","Marks",Date.valueOf("1999-11-30"),"657890654","1234@w.com","Cardiology");
//            t.addDoctor("John","Smith",Date.valueOf("1990-05-24"),"898645123","wsw@w.com","Gastrology");
//            t.addPatient("Tom","Johnson",Date.valueOf("1995-07-04"),"M");
//            t.addPatient("Eve","Poppins",Date.valueOf("2000-02-24"),"F");
//            t.addMedicine("2 x 200 ml",10);
//            t.addMedicine("1 x 200 mg",5);
//            t.addSupplier("Devil's Medicine","666 666 666","Bad St 666", "Hell", "00-666");
//            t.addSupplier("God's Medicine","555 555 555", "Holy St 2", "Heaven", "44-555");
//            Medicine med1 = d.getMedicineById(5);
//            Medicine med2 = d.getMedicineById(6);
//            Supplier sup1 = d.getSupplierById(7);
//            Supplier sup2 = d.getSupplierById(8);
//            t.addMedicineSupplier(med1,sup1);
//            t.addMedicineSupplier(med1,sup2);
//            t.addMedicineSupplier(med2,sup2);
//            List<Medicine> medicineList = new ArrayList<>();
//            medicineList.add(med1);
//            medicineList.add(med2);
//            List<String> dosagesList = new ArrayList<>();
//            dosagesList.add("3 x 100 ml");
//            dosagesList.add("1 x 200 mg");
//            t.addPrescription(Date.valueOf("2020-01-12"),Date.valueOf("2020-06-12"),1,
//                    4,medicineList,dosagesList);

//            dm.modifyDoctorById(12,"adam.doc@super.doc.com","Adam","123 321 123","Allergology","Doc");
//            dm.modifyPatientById(3,"Adam","Johnson");
//            dm.modifyPatientById(4,null,"Poppins");

//            List<PrescriptionElement> prescriptionList = d.getPrescriptionElements(9);
//            for(PrescriptionElement pe : prescriptionList){
//                System.out.println(pe);
//            }
//            List<PrescriptionElement> prescriptionList2 = d.getPrescriptionElements(10);
//            for(PrescriptionElement pe : prescriptionList2){
//                System.out.println(pe);
//            }

        }catch(Exception e){
            System.out.println(e);
        }
    }
}