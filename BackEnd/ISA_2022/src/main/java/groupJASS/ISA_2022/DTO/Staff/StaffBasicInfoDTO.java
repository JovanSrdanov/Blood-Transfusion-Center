package groupJASS.ISA_2022.DTO.Staff;

import groupJASS.ISA_2022.DTO.Address.AddressBloodDonorDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class StaffBasicInfoDTO {
    private UUID id;
    private String name;
    private String surname;
    private String phoneNumber;
    //Doesnt have coordinates
    private AddressBloodDonorDTO address;
    private String email;
}
