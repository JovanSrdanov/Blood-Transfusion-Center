package groupJASS.ISA_2022.DTO.Staff;

import groupJASS.ISA_2022.DTO.Address.AddressBloodDonorDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
public class StaffRegistrationDTO {
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]{8,}", message = "Length of the password must be 8 characters and it can only contain upppercase letters, lowercase letters and digits (0-9)")
    private String password;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String phoneNumber;
    //Doesnt have coordinates
    @Valid
    private AddressBloodDonorDTO address;
    @Email
    private String email;
}
