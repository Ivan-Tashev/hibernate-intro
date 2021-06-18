package course.springdata.hibernate;

import course.springdata.hibernate.entity.Student;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
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
        // 4. persist (INSERT) an entity
        Student student = new Student("Will Benson");
        session.beginTransaction();
        session.setHibernateFlushMode(FlushMode.MANUAL); // optional
        session.save(student);
        session.getTransaction().commit();
        // 5. read data (SELECT) entity by id
        session.beginTransaction();
        Student result = session.get(Student.class, 4L, LockMode.READ);
        session.getTransaction().commit();
        System.out.println(result);
        // 6. close session
        session.close();
    }
}