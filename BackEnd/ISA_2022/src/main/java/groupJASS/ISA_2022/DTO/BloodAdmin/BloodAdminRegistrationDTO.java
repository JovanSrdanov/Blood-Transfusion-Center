package groupJASS.ISA_2022.DTO.BloodAdmin;

import groupJASS.ISA_2022.DTO.AddressDTO;
import groupJASS.ISA_2022.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class BloodAdminRegistrationDTO {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private AddressDTO address;
    private String email;
}
