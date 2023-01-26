package groupJASS.ISA_2022.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Staff extends Person {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;

    @ManyToOne
    private BloodCenter bloodCenter;

    @Version
    Integer version;

    public void update(Staff updatedStaff) {
        this.name = updatedStaff.getName();
        this.surname = updatedStaff.getSurname();
        this.phoneNumber = updatedStaff.getPhoneNumber();
    }
}
