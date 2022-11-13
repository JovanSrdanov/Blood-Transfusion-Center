package groupJASS.ISA_2022.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BloodCenterRegistrationDTO {
    private String name;
    private AddressDTO address;
    private String description;
}