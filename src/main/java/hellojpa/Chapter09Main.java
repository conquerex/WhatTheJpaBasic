package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Chapter09Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            NewMember member = new NewMember();
            member.setUsername("nnnname");
            member.setHomeAddress(new NewAddress("ccc", "ddd", "eeee"));

            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            System.out.println("::::: e >>> " + e);
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
