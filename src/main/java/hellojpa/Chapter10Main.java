package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Chapter10Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            SampleMember member = new SampleMember();
            member.setUsername("jjjjjj");
            em.persist(member);


            List<SampleMember> resultList = em.createQuery(
                    "select m from SampleMember m where m.username like '%kim%'",
                    SampleMember.class
            ).getResultList();

            for (SampleMember m : resultList) {
                System.out.println("member = " + m);
            }


            // Criteria 사용 준비
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<SampleMember> query = cb.createQuery(SampleMember.class);

            //루트 클래스 (조회를 시작할 클래스)
            Root<SampleMember> m = query.from(SampleMember.class);

            //쿼리 생성
            CriteriaQuery<SampleMember> cq = query
                    .select(m)
                    .where(cb.equal(m.get("username"), "Park"));
            List<SampleMember> resultList2 = em.createQuery(cq).getResultList();

            // 네이티브 SQL
            em.createNativeQuery("select * from samplemember", SampleMember.class).getResultList();

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
