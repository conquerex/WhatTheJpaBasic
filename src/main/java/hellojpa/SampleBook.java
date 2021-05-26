package hellojpa;

import javax.persistence.Entity;

@Entity
public class SampleBook extends SampleItem {
    private String author;
    private String isbn;
}
