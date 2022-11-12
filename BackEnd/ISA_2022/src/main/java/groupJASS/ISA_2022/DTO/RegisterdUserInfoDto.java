package groupJASS.ISA_2022.DTO;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.Gender;
import groupJASS.ISA_2022.Model.RegisteredUser;
import org.hibernate.Hibernate;

import java.util.UUID;

public class RegisterdUserInfoDto {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String institution;
    private String jmbg;
    private Gender gender;
    private Address address;


    public RegisterdUserInfoDto(RegisteredUser registeredUser)
    {
        this.id = registeredUser.getId();
        this.name = registeredUser.getName();
        this.surname = registeredUser.getSurname();
        this.email = registeredUser.getEmail();
        this.phoneNumber = registeredUser.getPhoneNumber();
        this.institution = registeredUser.getInstitution();
        this.jmbg = registeredUser.getJmbg();
        this.gender = registeredUser.getGender();
        this.address = Hibernate.unproxy(registeredUser.getAddress(), Address.class);
    }
}
