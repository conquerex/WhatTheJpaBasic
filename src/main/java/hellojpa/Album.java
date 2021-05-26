package hellojpa;

import javax.persistence.Entity;

@Entity
public class Album extends SampleItem {
    private String artist;
}
