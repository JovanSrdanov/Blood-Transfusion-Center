package groupJASS.ISA_2022.DTO.Account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
public class PasswordDTO {
    @Pattern(regexp = "^[a-zA-Z0-9]{8,}", message = "Length of the password must be 8 characters and it can only contain upppercase letters, lowercase letters and digits (0-9)")
    private String newPassword;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,}", message = "Length of the password must be 8 characters and it can only contain upppercase letters, lowercase letters and digits (0-9)")
    private String confirmPassword;
}
