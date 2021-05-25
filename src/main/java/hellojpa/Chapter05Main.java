package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

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
//            team.getMembers().add(member); // 해당건은 제대로 매핑이 되지 않는다.(읽기전용) 오직 setTeam으로만 가능.
            em.persist(team);

            SampleMember member = new SampleMember();
            member.setUsername("member1");
//            member.setTeamId(team.getId());
//            member.changeTeam(team); // 연관관계 편의 메소드
            em.persist(member);

            // flush 및 clear가 없을 때는 위 객체가 1차 캐시에만 존재
            // team에서 member를 찾을 수 없다. 그러므로 아래처럼 다른 한쪽도 처리애햐 함
            // 처음 한 쪽 : member.setTeam(team);
//            team.getMembers().add(member); // 연관관계 편의 메소드 추가로 주석처리
            team.addMember(member); // 연관관계 편의 메소드

            // 1차 캐시가 아닌 DB에서 가지고 오는 방법
            em.flush(); // DB와 동기화
            em.clear(); // 영속성 컨텍스트 초기화

            SampleMember findMember = em.find(SampleMember.class, member.getId());
            Team findTeam = findMember.getTeam();
            System.out.println(">>>>>> findTeam = " + findTeam.getName());

            /**
             * 양방향 연관관계와 연관관계의 주인 1- 기본
             */
            List<SampleMember> members = findMember.getTeam().getMembers();
            for (SampleMember sampleMember : members) {
                System.out.println(">>>>>> sampleMember.getUsername() = " + sampleMember.getUsername());
            }

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
