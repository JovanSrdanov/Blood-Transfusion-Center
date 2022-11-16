package groupJASS.ISA_2022.DTO.Staff;

import groupJASS.ISA_2022.DTO.Address.AddressBloodDonorDTO;
import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterProfileDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class StaffProfileDTO {
    private UUID id;
    @NotEmpty
    String name;
    @NotEmpty
    String surname;
    @Email
    String email;
    @NotEmpty
    String phoneNumber;
    @Valid
    private AddressBloodDonorDTO address;
    BloodCenterProfileDto bloodCenter;
}
