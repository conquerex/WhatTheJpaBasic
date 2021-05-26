package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class Chapter08Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            SampleMember member = new SampleMember();
            member.setUsername("NNNNameee");
            member.setCreateBy("byby");
            member.setCreatedDate(LocalDateTime.now());
            em.persist(member);

            em.flush();
            em.clear();

            SampleMember refMember = em.getReference(SampleMember.class, member.getId());
            System.out.println("ferMember = " + refMember.getClass()); // Proxy
            System.out.println("isLoaded : " + emf.getPersistenceUnitUtil().isLoaded(refMember));
//            refMember.getUsername(); // 강제 호출
            Hibernate.initialize(refMember); // 강제 초기화
            System.out.println("isLoaded : " + emf.getPersistenceUnitUtil().isLoaded(refMember));

//            SampleMember findMember = em.find(SampleMember.class, member.getId());
//            System.out.println("findMember = " + findMember.getClass()); // Member
//
//            System.out.println("ref == find : " + (refMember == findMember));

            /**
             * 준영속 상태일 때
             * em.detach(refMember);
             * em.clear();
             * -------------------
             * <Exception>
             * could not initialize proxy [hellojpa.SampleMember#1] - the owning Session was closed
             */
            em.close();
            System.out.println("refMember = " + refMember.getUsername());

//            SampleMember member1 = em.find(SampleMember.class, member.getId());
//            System.out.println("member1.getId() = " + member1.getId());
//            System.out.println("member1.getUsername() = " + member1.getUsername());

//            SampleMember member2 = em.getReference(SampleMember.class, member.getId());
//            System.out.println("member2 = " + member2.getClass());
//            System.out.println("member2.getId() = " + member2.getId());
//            // 아래 username을 얻기 위해 select 쿼리가 실행됨
//            System.out.println("member2.getUsername() = " + member2.getUsername());

            tx.commit();
        } catch (Exception e) {
            System.out.println("::::: e >>> " + e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
