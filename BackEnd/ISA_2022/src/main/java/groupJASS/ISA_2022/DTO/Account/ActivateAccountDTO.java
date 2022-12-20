package groupJASS.ISA_2022.DTO.Account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class ActivateAccountDTO {

    private UUID activationCode;

    private UUID accountId;

}
