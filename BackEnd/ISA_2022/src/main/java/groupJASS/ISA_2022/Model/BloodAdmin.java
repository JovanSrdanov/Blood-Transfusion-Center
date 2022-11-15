package groupJASS.ISA_2022.Model;

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
public class BloodAdmin extends Person {
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;

    @ManyToOne
    private BloodCenter bloodCenter;
}
