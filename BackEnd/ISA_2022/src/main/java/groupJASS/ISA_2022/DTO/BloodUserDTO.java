package groupJASS.ISA_2022.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
public class BloodUserDTO {
    @NotEmpty
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,}", message = "Length of the password must be 8 characters and it can only contain upppercase letters, lowercase letters and digits (0-9)")
    private String password;
}
