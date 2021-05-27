package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS_HISTORY_NEW")
public class AddressEntity {

    public AddressEntity(String city, String street, String zipcode) {
        this.address = new NewAddress(city, street, zipcode);
    }

    @Id @GeneratedValue
    private Long id;

    private NewAddress address;

    public AddressEntity() {
        //
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NewAddress getAddress() {
        return address;
    }

    public void setAddress(NewAddress address) {
        this.address = address;
    }
}
