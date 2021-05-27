package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

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


            /**
             * 값 타입 컬렉션
             */
            NewMember myMember = new NewMember();
            myMember.setUsername("myyyyy");
            myMember.setHomeAddress(new NewAddress("HOME", "cccc", "eeeee"));

            myMember.getFavoriteFoods().add("food1");
            myMember.getFavoriteFoods().add("food22");
            myMember.getFavoriteFoods().add("food3");
            myMember.getFavoriteFoods().add("food444");

//            myMember.getAddressHistory().add(new NewAddress("old1", "ppp", "qqq"));
//            myMember.getAddressHistory().add(new NewAddress("old2", "xxxx", "sdsd"));
            myMember.getAddressHistory().add(new AddressEntity("old1", "ppp", "qqq"));
            myMember.getAddressHistory().add(new AddressEntity("old2", "xxxx", "sdsd"));

            em.persist(myMember);

            em.flush();
            em.clear();

            System.out.println("============  findMember  ============");
            NewMember findMember = em.find(NewMember.class, myMember.getId());

//            List<NewAddress> addressHistory = findMember.getAddressHistory();
//            for (NewAddress a : addressHistory) {
//                System.out.println("  >>>>>  a = " + a.getCity());
//            }

            Set<String> foods = findMember.getFavoriteFoods();
            for (String f : foods) {
                System.out.println("  >>>>>  f = " + f);
            }

            // old home city --> new city
            NewAddress oldAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new NewAddress("NNNN", oldAddress.getStreet(), oldAddress.getZipcode()));

            // old collection --> new
            findMember.getFavoriteFoods().remove("food1");
            findMember.getFavoriteFoods().add("food55");

            // old history --> new
            // 전부 삭제 후 컬렉션에 있는 것을 insert
//            findMember.getAddressHistory().remove(new NewAddress("old1", "ppp", "qqq"));
//            findMember.getAddressHistory().add(new NewAddress("newwww city", "ppp", "qqq"));


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
