package jpa;

import jpa.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school_jpa");
        EntityManager em = emf.createEntityManager();
        // 1.Insert into table
        em.getTransaction().begin(); // start transaction
        Student student = new Student("Mary Jane", new Date());
        em.persist(student); // persist entity
        em.getTransaction().commit();
        // 2. get entity by id
        em.getTransaction().begin();
        Student found = em.find(Student.class, 3L);
        System.out.println(found);
        em.getTransaction().commit();
        // 3. get by name or specific query
        em.getTransaction().begin();
        em.createQuery("SELECT s FROM Student as s WHERE s.name LIKE :name ORDER BY s.id DESC")
                .setParameter("name", "%")
                .getResultList().forEach(System.out::println);
        em.getTransaction().commit();
        // 4. remove
        em.getTransaction().begin();
        em.remove(em.find(Student.class, 2L));
        em.getTransaction().commit();
        // 5. merge
        em.getTransaction().begin();
        em.detach(found);
        found.setName("Bill Haris");
        em.merge(found);
        em.getTransaction().commit();
        // 6. close EntityManager
        em.close();
    }
}