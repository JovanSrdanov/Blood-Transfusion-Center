package groupJASS.ISA_2022.DTO.BloodAdmin;

import groupJASS.ISA_2022.DTO.AddressDTO;
import groupJASS.ISA_2022.DTO.AddressRegUserDTO;
import groupJASS.ISA_2022.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class BloodAdminRegistrationDTO {
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String phoneNumber;
    //Doesnt have coordinates
    @Valid
    private AddressRegUserDTO address;
    @Email
    private String email;
}
