package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD",
            joinColumns = @JoinColumn(name = "MEMBER_ID")
    )
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS_HISTORY",
//            joinColumns = @JoinColumn(name = "MEMBER_ID")
//    )
//    private List<NewAddress> addressHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

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

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

//    public List<NewAddress> getAddressHistory() {
//        return addressHistory;
//    }
//
//    public void setAddressHistory(List<NewAddress> addressHistory) {
//        this.addressHistory = addressHistory;
//    }

    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }
}
