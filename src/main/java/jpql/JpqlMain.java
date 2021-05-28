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
            Team teamA = new Team("aaa");
            em.persist(teamA);

            Team teamB = new Team("bbb");
            em.persist(teamB);

            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setAge((int) (Math.random() * 10000));

                if (i > 50) member.setUsername("aaa");
                else if (i % 3 == 1) member.setUsername(null);
                else member.setUsername("My name " + i);

                if (i % 2 == 0) member.setTeam(teamA);
                else member.setTeam(teamB);

                if (i % 3 == 0) member.setType(MemberType.ADMIN);

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

            /**
             * 프로젝션
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

            /**
             * 조인
             */
            em.flush();
            em.clear();
//            String queryString = "select m from J_MEMBER m left join m.team t";
//            String queryString = "select m from J_MEMBER m, J_TEAM t where m.username = t.name";
            String queryString = "select m from J_MEMBER m left join J_TEAM t on m.username = t.name";
            em.createQuery(queryString, Member.class).getResultList();

            /**
             * JPQL 타입 표현과 기타식
             */
            String queryString2 = "select m.username, 'SAMPLE', TRUE from J_MEMBER m " +
                    "where m.type = :userType"; // "where m.type = jpql.MemberType.ADMIN"
            List<Object[]> resultList = em.createQuery(queryString2)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();
//            for (Object[] objects : resultList) {
//                System.out.println("objects = " + objects[0]);
//                System.out.println("objects = " + objects[1]);
//                System.out.println("objects = " + objects[2]);
//            }

            /**
             * 조건식(CASE 등등)
             */
            em.flush();
            em.clear();

            String queryString3 =
                    "select " +
                            "case when m.age <= 1000 then '학생요금' " +
                            "     when m.age > 6000 then '경로요금' " +
                            "     else '일반요금' " +
                            "end " +
                            "from J_MEMBER m " +
                            "where m.id < 20 ";
//            List<String> resultList4 = em.createQuery(queryString3, String.class).getResultList();
//            for (String s : resultList4) {
//                System.out.println(">>>>> s = " + s);
//            }

            String queryString5 =
                    "select " +
                            "coalesce(m.username, '없음') " +
                            "from J_MEMBER m " +
                            "where m.id < 20 ";
//            List<String> resultList5 = em.createQuery(queryString5, String.class).getResultList();
//            for (String s : resultList5) {
//                System.out.println(">>>>> s = " + s);
//            }


            /**
             * JPQL 함수
             */
//            String queryString6 = "select concat(m.id, m.username) from J_MEMBER m where m.id < 11";
//            List<String> resultList6 = em.createQuery(queryString6, String.class).getResultList();
//            for (String s : resultList6) {
//                System.out.println("s = " + s);
//            }

//            String queryString7 = "select size(t.members) from J_TEAM t";
//            List<Integer> resultList7 = em.createQuery(queryString7, Integer.class).getResultList();
//            for (Integer s : resultList7) {
//                System.out.println("s = " + s);
//            }

//            String queryString7 = "select function('group_concat', m.username) from J_MEMBER m ";
            String queryString7 = "select group_concat(m.username) from J_MEMBER m ";
            List<String> resultList7 = em.createQuery(queryString7, String.class).getResultList();
            for (String s : resultList7) {
                System.out.println("s = " + s);
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
