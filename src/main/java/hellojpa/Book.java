package hellojpa;

import javax.persistence.Entity;

@Entity
public class Book extends SampleItem {
    private String author;
    private String isbn;
}
