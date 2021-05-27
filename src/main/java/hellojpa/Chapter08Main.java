package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

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
//            em.close();
//            System.out.println("refMember = " + refMember.getUsername());

//            SampleMember member1 = em.find(SampleMember.class, member.getId());
//            System.out.println("member1.getId() = " + member1.getId());
//            System.out.println("member1.getUsername() = " + member1.getUsername());

//            SampleMember member2 = em.getReference(SampleMember.class, member.getId());
//            System.out.println("member2 = " + member2.getClass());
//            System.out.println("member2.getId() = " + member2.getId());
//            // 아래 username을 얻기 위해 select 쿼리가 실행됨
//            System.out.println("member2.getUsername() = " + member2.getUsername());

            /**
             * 즉시 로딩과 지연 로딩
             */
            System.out.println("================");

            Team team = new Team();
            team.setName("TTTeam");
            em.persist(team);

            Team teamB = new Team();
            teamB.setName("TTTeaMM");
            em.persist(teamB);

            SampleMember member1 = new SampleMember();
            member1.setUsername("no11111");
            member1.setCreateBy("mmmm");
            member1.setCreatedDate(LocalDateTime.now());
            member1.setTeam(team);
            em.persist(member1);

            SampleMember member2 = new SampleMember();
            member2.setUsername("no2222");
            member2.setCreateBy("nnn");
            member2.setCreatedDate(LocalDateTime.now());
            member2.setTeam(teamB);
            em.persist(member2);

            em.flush();
            em.clear();

            SampleMember m = em.find(SampleMember.class, member2.getId());
            System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());

            System.out.println(">>>>>>>>>>>>>");
            System.out.println("Team name ::: " + m.getTeam().getName()); // LAZY의 경우, 프록시로 가지고 옴. 초기화
            System.out.println(">>>>>>>>>>>>>");

            em.flush();
            em.clear();

            List<SampleMember> memberList = em.createQuery("select m from SampleMember m join fetch m.team", SampleMember.class)
                    .getResultList();


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
