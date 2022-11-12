package groupJASS.ISA_2022.DTO;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.Gender;
import groupJASS.ISA_2022.Model.RegisteredUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.UUID;
@NoArgsConstructor
@Getter
@Setter
public class RegisteredUserDTO {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String institution;
    private String jmbg;
    private Gender gender;
    private Address address;


    public RegisteredUserDTO(RegisteredUser registeredUser)
    {
        this.id = registeredUser.getId();
        this.name = registeredUser.getName();
        this.surname = registeredUser.getSurname();
        this.email = registeredUser.getEmail();
        this.phoneNumber = registeredUser.getPhoneNumber();
        this.institution = registeredUser.getInstitution();
        this.jmbg = registeredUser.getJmbg();
        this.gender = registeredUser.getGender();
    }
}
