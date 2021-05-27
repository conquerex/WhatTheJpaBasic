package hellojpa;

import javax.persistence.*;

@Entity
public class NewMember {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Embedded
    private NewPeriod workPeriod;

    @Embedded
    private NewAddress homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city",
                    column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street",
                    column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode",
                    column = @Column(name = "WORK_ZIPCODE"))
    })
    private NewAddress workAddress;


    // 기간 Period
//    private LocalDateTime startDate;
//    private LocalDateTime endDate;

    // 주소
//    private String city;
//    private String street;
//    private String zipcode;


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

    public NewPeriod getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(NewPeriod workPeriod) {
        this.workPeriod = workPeriod;
    }

    public NewAddress getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(NewAddress homeAddress) {
        this.homeAddress = homeAddress;
    }

    public NewAddress getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(NewAddress workAddress) {
        this.workAddress = workAddress;
    }
}
