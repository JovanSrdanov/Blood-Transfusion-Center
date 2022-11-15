package groupJASS.ISA_2022.DTO;

import groupJASS.ISA_2022.Model.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class UpdateRegisterdUserInfoDto {
    @NotEmpty
    private UUID id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String institution;
    @Pattern(regexp = "^[0-9]{13}", message = "length must be 13 and it can only contain numbers")
    private String jmbg;
    @NotEmpty
    private Gender gender;
    @Valid
    private AddressUpdateDto address;
}
