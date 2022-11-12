package groupJASS.ISA_2022.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class RegisteredUser extends Person {

    // TODO: ne radi lazy
    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;
    private String institution;
    private String jmbg;
    private Gender gender;

}
