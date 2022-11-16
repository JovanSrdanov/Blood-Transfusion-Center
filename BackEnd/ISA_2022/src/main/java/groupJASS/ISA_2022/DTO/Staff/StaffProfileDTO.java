package groupJASS.ISA_2022.DTO.Staff;

import groupJASS.ISA_2022.DTO.Address.AddressBloodDonorDTO;
import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterProfileDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[0-9]{9}$", message = "Phone number can only contain 9 digits (0-9)")
    String phoneNumber;
    @Valid
    private AddressBloodDonorDTO address;
    BloodCenterProfileDto bloodCenter;
}
