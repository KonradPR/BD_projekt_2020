import Entities.*;
import Handlers.DataAccessHandler;
import Handlers.DataModificationHandler;
import Handlers.TransactionHandler;
import LogicUtils.Parser;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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

         }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}