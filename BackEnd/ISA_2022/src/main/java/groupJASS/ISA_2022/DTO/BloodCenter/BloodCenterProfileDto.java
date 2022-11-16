package groupJASS.ISA_2022.DTO.BloodCenter;

import groupJASS.ISA_2022.DTO.Address.AddressDTO;
import groupJASS.ISA_2022.Model.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class BloodCenterProfileDto {
    private UUID id;
    @NotEmpty
    private String name;
    @NotEmpty
    private  String description;
    @Valid
    private AddressDTO address;
    private double rating;
}
