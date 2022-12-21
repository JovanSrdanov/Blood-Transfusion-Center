package groupJASS.ISA_2022.DTO.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDTO {

    @Email
    private String email;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,}", message = "Length of the password must be 8 characters and it can only contain upppercase letters, lowercase letters and digits (0-9)")
    private String password;
}
