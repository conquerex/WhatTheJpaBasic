package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class Chapter07Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Movie movie = new Movie();
            movie.setDirector("aaaa");
            movie.setActor("dddd");
            movie.setName("NNNName");
            movie.setPrice(2000);
            em.persist(movie);

            em.flush();
            em.clear();

            Movie movie1 = em.find(Movie.class, movie.getId());
            System.out.println("movie1 = " + movie1);
            System.out.println("movie1 = " + movie1.getName());
            System.out.println("movie1 = " + movie1.getActor());

            SampleMember member = new SampleMember();
            member.setUsername("NNNNameee");
            member.setCreateBy("byby");
            member.setCreatedDate(LocalDateTime.now());
            em.persist(member);

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
