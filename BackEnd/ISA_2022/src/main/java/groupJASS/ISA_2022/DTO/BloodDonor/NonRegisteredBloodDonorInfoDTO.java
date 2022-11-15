package groupJASS.ISA_2022.DTO.BloodDonor;

import groupJASS.ISA_2022.Model.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
public class NonRegisteredBloodDonorInfoDTO {

    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;

    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String institution;
    @NotEmpty
    private String occupation;

    @Pattern(regexp = "^[0-9]{13}", message = "length must be 13 and it can only contain numbers")
    private String jmbg;

    @NotNull
    private Gender gender;

}
