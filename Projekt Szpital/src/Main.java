import Entities.*;
import Handlers.DataAccessHandler;
import Handlers.TransactionHandler;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
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
            t.addDoctor("Karl","Marks",Date.valueOf("1999-11-30"),"657890654","1234@w.com","Cardiology");
            //t.addDoctor("John","Smith",Date.valueOf("1990-05-24"),"898645123","wsw@w.com","Gastrology");
            //t.addPatient("Tom","Johnson",Date.valueOf("1995-07-04"),"M");
            //t.addPatient("Eve","Poppins",Date.valueOf("2000-02-24"),"F");


        }catch(Exception e){
            System.out.println(e);
        }
    }
}