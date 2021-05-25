package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@SequenceGenerator(name = "member_seq_gengerator", sequenceName = "member_seq")
public class SampleMember {

    public SampleMember() {
        // JPA는 기본 생성자가 필요
        // 리플렉션등의 이유로 동적으로 처리되어야 해서
    }

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_gengerator")
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*
    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // date : 예전 버전
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // date : 최신 버전
    private LocalDate testDate;
    private LocalDateTime testTime;

    @Lob
    private String description;
    */
}
