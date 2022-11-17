package groupJASS.ISA_2022.DTO.BloodDonor;

import groupJASS.ISA_2022.DTO.Account.AccountDTO;
import groupJASS.ISA_2022.DTO.Address.AddressBloodDonorDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;

@NoArgsConstructor
@Getter
@Setter
public class RegisterBloodDonorDTO {
    @Valid
    private AccountDTO accountDTO;
    @Valid
    private NonRegisteredBloodDonorInfoDTO nonRegisteredBloodDonorInfoDTO;
    @Valid
    private AddressBloodDonorDTO addressBloodDonorDTO;


}
