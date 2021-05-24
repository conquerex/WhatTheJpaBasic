package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member {

    public Member() {
        // JPA는 기본 생성자가 필요
        // 리플렉션등의 이유로 동적으로 처리되어야 해서
    }

    @Id
    private Long id;

    @Column(name = "name")
    private String username;

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
}
