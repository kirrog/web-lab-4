import beansLab.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class HibernateOracleTestXML {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM User", User.class);
            List users = query.list();
            System.out.println(users.size());
            transaction.commit();
            session.close();
            factory.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static String seqUserCheck = "Select * from user_objects where Object_Name = 'SEQ_USER'";

}