package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "member_seq_gengerator", sequenceName = "member_seq")
public class SampleMember extends SampleBaseEntity {

    public SampleMember() {
        // JPA는 기본 생성자가 필요
        // 리플렉션등의 이유로 동적으로 처리되어야 해서
    }

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    // 다대일 케이스 - 읽기 전용 매핑
//    @ManyToOne
//    @JoinColumn(name = "TEAM_ID")
//    private Team team;

    // 일대다 케이스
    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn // (name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    //    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT")
//    private List<Product> products = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();


    /**
     * Getter / Setter
     */

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

//    public Long getTeamId() {
//        return teamId;
//    }
//
//    public void setTeamId(Long teamId) {
//        this.teamId = teamId;
//    }

/*
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void changeTeam(Team team) {
        this.team = team;

        // 연관관계 편의 메소드
        team.getMembers().add(this);
    }
*/


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
