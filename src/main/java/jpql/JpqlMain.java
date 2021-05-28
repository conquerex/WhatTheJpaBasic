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
            Team team = new Team("team name");
            em.persist(team);

            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("My name " + i);
                member.setAge((int) (Math.random() * 10000));
                member.setTeam(team);
                em.persist(member);
            }

            TypedQuery<Member> query = em.createQuery("select m from J_MEMBER m where m.username = :username", Member.class);
            query.setParameter("username", "My name");

/*
            Member singleResult = query.getSingleResult();
            List<Member> resultList = query.getResultList();
            Query query2 = em.createQuery("select m.username, m.age from J_MEMBER m");
            resultList.get(0).setAge(14);

            for (Member mem : resultList) {
                System.out.println("  >>>> mem.getUsername() = " + mem.getUsername());
                System.out.println("  >>>> mem.getAge() = " + mem.getAge());
            }
*/

            System.out.println("=============  프로젝션  =============");
            em.createQuery("select m.team from J_MEMBER m", Team.class).getResultList();
            em.createQuery("select t from J_MEMBER m join m.team t", Team.class).getResultList();
            em.createQuery("select o.address from J_ORDER o", Address.class).getResultList();

            List<Object[]> resultList1 = em.createQuery("select distinct m.username, m.age from J_MEMBER m").getResultList();
            System.out.println("  >>> username >>>> " + resultList1.get(0)[0]);
            System.out.println("  >>> age      >>>> " + resultList1.get(0)[1]);

            List<MemberDto> resultList2 = em.createQuery("select new jpql.MemberDto(m.username, m.age) from J_MEMBER m", MemberDto.class).getResultList();
            for (MemberDto memberDto : resultList2) {
                System.out.println(">>>> memberDto.getUsername() = " + memberDto.getUsername());
            }

            em.flush();
            em.clear();

            List<Member> resultList3 = em.createQuery("select m from J_MEMBER m order by m.age desc ", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(5)
                    .getResultList();

            for (Member member : resultList3) {
                System.out.println("member.toString() = " + member.toString());
            }

            tx.commit();
        } catch (Exception e) {
            System.out.println("####### e = " + e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
