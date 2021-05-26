package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends SampleBaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

//    @OneToMany(mappedBy = "team")
    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<SampleMember> members = new ArrayList<>();

/*
    // 연관관계 편의 메소드
    public void addMember(SampleMember member) {
        member.setTeam(this);
        members.add(member);
    }
*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SampleMember> getMembers() {
        return members;
    }

    public void setMembers(List<SampleMember> members) {
        this.members = members;
    }
}
