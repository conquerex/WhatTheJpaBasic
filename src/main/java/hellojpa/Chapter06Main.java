package hellojpa;

import jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Chapter06Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /**
             * 일대다
             */
            SampleMember member = new SampleMember();
            member.setUsername("jjjj");
            em.persist(member);

            Team team = new Team();
            team.setName("tttmmmm");
            // 예상대로 inset 2회
            // 그리고 update 1회!!!
            team.getMembers().add(member);
            em.persist(team);

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
