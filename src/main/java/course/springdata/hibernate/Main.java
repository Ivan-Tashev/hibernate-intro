package course.springdata.hibernate;

import course.springdata.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();
        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        session.getTransaction().begin();
        session.save(new Student("John Smith")); // INSERT
        Student student = session.get(Student.class, 5L); // SELECT by Id = 5
        session.createQuery("FROM Student WHERE name Like :pattern", Student.class)
                .setParameter("pattern", "J%" )
                .stream().forEach(System.out::println); // SELECT all as stream
        session.getTransaction().commit();
        session.close();
    }
}
