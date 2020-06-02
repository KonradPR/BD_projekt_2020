import Gui.AppFrame;
import Handlers.TransactionHandler;
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
        try{
            AppFrame af = new AppFrame();
            af.show(args);
         }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}