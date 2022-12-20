package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.ActivateAccount;

import java.util.UUID;

public interface IActivateAccountService extends ICrudService<ActivateAccount> {
    boolean existsByAccountIdAndAcctivationCode(UUID AccountId, UUID AcctivationCode);
}
