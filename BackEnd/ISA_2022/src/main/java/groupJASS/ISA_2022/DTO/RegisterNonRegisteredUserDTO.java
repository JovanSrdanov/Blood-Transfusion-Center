package groupJASS.ISA_2022.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;

@NoArgsConstructor
@Getter
@Setter
public class RegisterNonRegisteredUserDTO {
    @Valid
    private BloodUserDTO bloodUserDTO;
    @Valid
    private NonRegisteredUserInfoDTO nonRegisteredUserInfoDTO;
    @Valid
    private AddressRegUserDTO addressRegUserDTO;


}
