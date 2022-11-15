package groupJASS.ISA_2022.DTO.BloodDonor;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Model.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class BloodDonorInfoDto {
    private UUID id;
    private String name;
    private String surname;

    private String phoneNumber;
    private String institution;
    private String jmbg;
    private Gender gender;
    private Address address;


    public BloodDonorInfoDto(BloodDonor bloodDonor) {
        this.id = bloodDonor.getId();
        this.name = bloodDonor.getName();
        this.surname = bloodDonor.getSurname();

        this.phoneNumber = bloodDonor.getPhoneNumber();
        this.institution = bloodDonor.getInstitution();
        this.jmbg = bloodDonor.getJmbg();
        this.gender = bloodDonor.getGender();
        this.address = Hibernate.unproxy(bloodDonor.getAddress(), Address.class);
    }
}
