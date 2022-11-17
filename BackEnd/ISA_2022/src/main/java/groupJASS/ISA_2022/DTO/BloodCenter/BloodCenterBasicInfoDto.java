package groupJASS.ISA_2022.DTO.BloodCenter;

import groupJASS.ISA_2022.Model.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class BloodCenterBasicInfoDto {
    private UUID id;
    private String name;
    private Address address;
    private double rating;
}
