package groupJASS.ISA_2022.DTO;

import groupJASS.ISA_2022.Model.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BloodCentarBasicInfoDto {
    private String name;
    private Address address;
    private double rating;
}
