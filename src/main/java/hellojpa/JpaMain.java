package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("Sample");
//
//            // 영속
//            System.out.println(">>>> before");
//            em.persist(member);
//            System.out.println(">>>> after");

            // 별도의 Select 쿼리 없이 조회
            Member findMember = em.find(Member.class, 101L);
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());

            System.out.println(">>>>>>> findMember2 ; select 쿼리 있음 >>>>>>>");
            Member findMember2 = em.find(Member.class, 2L);
            System.out.println("findMember.getId() = " + findMember2.getId());
            System.out.println("findMember.getName() = " + findMember2.getName());

            System.out.println(">>>>>>> findMember3 ; select 쿼리 X >>>>>>>");
            Member findMember3 = em.find(Member.class, 2L);
            System.out.println("findMember.getId() = " + findMember3.getId());
            System.out.println("findMember.getName() = " + findMember3.getName());

            System.out.println(">>>>> findMember2 == findMember3 : ");
            System.out.println(">>>>> " + (findMember2 == findMember3));

//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(100)
//                    .getResultList();
//
//            for (Member m : result) {
//                System.out.println("member >>>>> " + m.getName());
//            }

            /**
             * 엔티티 등록 예시
             */
//            Member member1 = new Member(201L, "aaa");
//            Member member2 = new Member(202L, "bbbb");
//            em.persist(member1);
//            em.persist(member2);
//            System.out.println("========= After em.persist =======");

            /**
             * 엔티티 수정 예시
             */
            Member sample = em.find(Member.class, 101L);
            sample.setName("qqqq");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
