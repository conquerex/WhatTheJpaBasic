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
            SampleMovie sampleMovie = new SampleMovie();
            sampleMovie.setDirector("aaaa");
            sampleMovie.setActor("dddd");
            sampleMovie.setName("NNNName");
            sampleMovie.setPrice(2000);
            em.persist(sampleMovie);

            em.flush();
            em.clear();

            SampleMovie sampleMovie1 = em.find(SampleMovie.class, sampleMovie.getId());
            System.out.println("movie1 = " + sampleMovie1);
            System.out.println("movie1 = " + sampleMovie1.getName());
            System.out.println("movie1 = " + sampleMovie1.getActor());

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
