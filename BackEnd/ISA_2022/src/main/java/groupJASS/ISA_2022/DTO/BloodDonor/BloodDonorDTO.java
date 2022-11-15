package groupJASS.ISA_2022.DTO.BloodDonor;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class BloodDonorDTO {
    private UUID id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String institution;
    private String jmbg;
    private Gender gender;
    private Address address;


}
