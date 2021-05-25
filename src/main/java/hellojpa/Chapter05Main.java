package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Chapter05Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /**
             * 단방향 연관관계
             */
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            SampleMember member = new SampleMember();
            member.setUsername("member1");
//            member.setTeamId(team.getId());
            member.setTeam(team);
            em.persist(member);

            // 1차 캐시가 아닌 DB에서 가지고 오는 방법
            em.flush(); // DB와 동기화
            em.close(); // 영속성 컨텍스트 초기화

            SampleMember findMember = em.find(SampleMember.class, member.getId());
            Team findTeam = findMember.getTeam();
            System.out.println(">>>>>> findTeam = " + findTeam.getName());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
