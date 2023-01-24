package groupJASS.ISA_2022.DTO.BloodDonor;

import groupJASS.ISA_2022.DTO.Address.AddressUpdateDto;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Model.Gender;
import groupJASS.ISA_2022.Utilities.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BloodDonorInfoDto {
    private UUID id;
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String institution;
    @Pattern(regexp = "^[0-9]{13}", message = "length must be 13 and it can only contain numbers")
    private String jmbg;
    @NotNull
    private Gender gender;
    @NotEmpty
    private String occupation;
    @Valid
    private AddressUpdateDto address;
    private int points;

    private int penalties;

    public BloodDonorInfoDto(UUID id, String email, String name, String surname, String phoneNumber, String institution, String jmbg, Gender gender, String occupation, Address address, int points, int penalties) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.institution = institution;
        this.jmbg = jmbg;
        this.gender = gender;
        this.occupation = occupation;
        this.address = ObjectMapperUtils.map(Hibernate.unproxy(address, Address.class), AddressUpdateDto.class);
        this.points = points;
        this.penalties = penalties;
    }

    public BloodDonorInfoDto(BloodDonor bloodDonor, String email) {
        this.id = bloodDonor.getId();
        this.name = bloodDonor.getName();
        this.surname = bloodDonor.getSurname();
        this.phoneNumber = bloodDonor.getPhoneNumber();
        this.institution = bloodDonor.getInstitution();
        this.jmbg = bloodDonor.getJmbg();
        this.gender = bloodDonor.getGender();
        this.occupation = bloodDonor.getOccupation();
        this.points = bloodDonor.getPoints();
        this.email = email;
        this.penalties = bloodDonor.getPenalties();
        this.address = ObjectMapperUtils.map(Hibernate.unproxy(bloodDonor.getAddress(), Address.class), AddressUpdateDto.class);
    }
}
