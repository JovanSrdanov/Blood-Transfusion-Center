package groupJASS.ISA_2022.DTO.BloodCenter;

import groupJASS.ISA_2022.DTO.AddressDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
public class BloodCenterRegistrationDTO {
    @NotEmpty
    private String name;
    @Valid
    private AddressDTO address;
    @NotEmpty
    private String description;
}