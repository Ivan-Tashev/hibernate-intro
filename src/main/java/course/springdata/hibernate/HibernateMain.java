package course.springdata.hibernate;

import course.springdata.hibernate.entity.Student;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class HibernateMain {
    public static void main(String[] args) {
        // 1. create Hibernate config
        Configuration cfg = new Configuration();
        cfg.configure(); // get configuration from file: hibernate.cfg.xml
        // 2. create Hibernate Session Factory
        SessionFactory sf = cfg.buildSessionFactory();
        // 3. create Hibernate Session
        Session session = sf.openSession();
        // 4.1 persist (INSERT) an entity
        Student student = new Student("Peter Cold");
        session.beginTransaction();
        // session.setHibernateFlushMode(FlushMode.MANUAL);  optional
        session.save(student);
        session.getTransaction().commit();
        // 4.2 read data (SELECT) entity by id
        session.beginTransaction();
        Student result = session.get(Student.class, 4L, LockMode.READ);
        session.getTransaction().commit();
        System.out.println(result);
        // 4.3. List all student using HQL
        session.beginTransaction();
        session.createQuery("FROM Student ", Student.class)
                .setFirstResult(4) // from result
                .setMaxResults(2) // to number of results
                .stream().forEach(System.out::println);
        session.getTransaction().commit();
            // with WHERE clause
        session.createQuery("FROM Student WHERE name = :name", Student.class)
                .setParameter("name", "Peter Cold")
                .stream().forEach(System.out::println);
        // 4.4 Type-safe criteria queries
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> query = cb.createQuery(Student.class);
        Root<Student> student_ = query.from(Student.class);
        query.select(student_).where(cb.like(student_.get("name"), "W%"));
        session.createQuery(query).getResultStream()
                .forEach(System.out::println);
        // 5. close session
        session.close();
    }
}