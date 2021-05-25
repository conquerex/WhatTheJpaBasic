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

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

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

//    public Long getTeamId() {
//        return teamId;
//    }
//
//    public void setTeamId(Long teamId) {
//        this.teamId = teamId;
//    }

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
