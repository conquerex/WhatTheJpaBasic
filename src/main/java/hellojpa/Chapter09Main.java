package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Chapter09Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            NewAddress address = new NewAddress("ccc", "ddd", "eeee");

            NewMember member = new NewMember();
            member.setUsername("nnnname");
            member.setHomeAddress(address);
            em.persist(member);

            // 불변객체 변경하기
            NewAddress newAddress = new NewAddress("Ciiiity", address.getStreet(), address.getZipcode());
            member.setHomeAddress(newAddress);


            NewAddress address2 = new NewAddress(address.getCity(), address.getStreet(), address.getZipcode());

            NewMember member2 = new NewMember();
            member2.setUsername("mymy");
            member2.setHomeAddress(address2);
            em.persist(member2);

//            member.getHomeAddress().setCity("ccccity");

            /**
             * 값 타입의 비교
             */
            NewAddress a1 = new NewAddress("ccc", "ddd", "eeee");
            NewAddress a2 = new NewAddress("ccc", "ddd", "eeee");
            System.out.println(">>>>> a1 == a2    ::: " + (a1 == a2));
            System.out.println(">>>>> a1 equal a2 ::: " + a1.equals(a2));

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
