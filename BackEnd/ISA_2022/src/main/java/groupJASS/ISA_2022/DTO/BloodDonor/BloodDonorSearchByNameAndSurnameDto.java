package groupJASS.ISA_2022.DTO.BloodDonor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BloodDonorSearchByNameAndSurnameDto {
    private String name;
    private String surname;
}
