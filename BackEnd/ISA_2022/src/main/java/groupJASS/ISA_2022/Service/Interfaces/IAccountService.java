package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.DTO.Account.AccountDTO;
import groupJASS.ISA_2022.DTO.Account.ActivateAccountDTO;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.BloodDonor;

import java.util.UUID;

public interface IAccountService extends ICrudService<Account> {

    Account registerAccount(AccountDTO accountDto, String roleName, UUID personId);
    boolean checkEmailAvailability(String email);
    Account findAccountByPersonId(UUID personId);
    Account findAccountByEmail(String email);
    Account activateAccount(ActivateAccountDTO activateAccountDTO);
}
