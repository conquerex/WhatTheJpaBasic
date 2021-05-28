package jpql;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("My name");
            member.setAge(22);
            em.persist(member);

            TypedQuery<Member> query = em.createQuery("select m from J_MEMBER m where m.username = :username", Member.class);
            query.setParameter("username", "My name");
//            Member singleResult = query.getSingleResult();
            List<Member> resultList = query.getResultList();
            Query query2 = em.createQuery("select m.username, m.age from J_MEMBER m");

            for (Member mem : resultList) {
                System.out.println("  >>>> mem.getUsername() = " + mem.getUsername());
                System.out.println("  >>>> mem.getAge() = " + mem.getAge());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
