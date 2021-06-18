package course.springdata.hibernate.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateMain {
    public static void main(String[] args) {
        // 1. create Hibernate config
        Configuration cfg = new Configuration();
        cfg.configure(); // get configuration from file: hibernate.cfg.xml

        // 2. create Hibernate Session Factory
        SessionFactory sf = cfg.buildSessionFactory();

        // 3. create Hibernate Session
        Session session = sf.openSession();

        // 4. persist an entity
        Student student = new Student("John Doe");
        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();

        // 5. close session
        session.close();
    }
}