package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.BloodDonor;

import java.util.UUID;

public interface IAccountService extends ICrudService<Account> {
    void registerNewUser(Account account);

    void registerRegisteredUser(Account map, BloodDonor bloodDonor);
    public boolean checkEmailAvailability(String email);
    public Account findAccountByPersonId(UUID personId);
}
