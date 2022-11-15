package groupJASS.ISA_2022.DTO.BloodAdmin;

import groupJASS.ISA_2022.DTO.AddressRegUserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@NoArgsConstructor
@Getter
@Setter
public class BloodAdminBasicInfoDTO {
    private UUID id;
    private String name;
    private String surname;
    private String phoneNumber;
    //Doesnt have coordinates
    private AddressRegUserDTO address;
    private String email;
}
