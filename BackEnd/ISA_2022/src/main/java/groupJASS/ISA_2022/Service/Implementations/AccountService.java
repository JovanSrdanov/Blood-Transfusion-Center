package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.DTO.Account.ActivateAccountDTO;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Model.Role;
import groupJASS.ISA_2022.Repository.AccountRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IActivateAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@Primary
public class AccountService implements IAccountService {

    private final AccountRepository _accountRepository;
    private final IActivateAccountService _activateAccountService;
    @Autowired
    private PasswordEncoder _passwordEncoder;
    @Autowired
    private IRoleService _roleService;

    @Autowired
    public AccountService(AccountRepository accountRepository, IRoleService roleService, PasswordEncoder passwordEncoder, IActivateAccountService activateAccountService) {
        _accountRepository = accountRepository;
        _roleService = roleService;
        this._passwordEncoder = passwordEncoder;
        _activateAccountService = activateAccountService;
    }

    @Override
    public Iterable<Account> findAll() {
        return _accountRepository.findAll();
    }

    @Override
    public Account findById(UUID id) {
        if (_accountRepository.findById(id).isPresent()) {

            return _accountRepository.findById(id).get();
        }

        throw new NotFoundException("User not found");
    }

    @Override
    public Account save(Account entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        return _accountRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _accountRepository.deleteById(id);
    }

    @Override
    public Account registerNewUser(Account account) {


        if (_accountRepository.existsAccountByEmail(account.getEmail())) {

            throw new IllegalArgumentException("Account with this email already exists");
        }
        account.setActivated(false);
        account.setPassword(_passwordEncoder.encode(account.getPassword()));
        return save(account);

    }

    @Override
    public Account registerRegisteredUser(Account map, BloodDonor bloodDonor) {
        map.setPersonId(bloodDonor.getId());
        List<Role> roles = _roleService.findByName("ROLE_BLOOD_DONOR");
        map.setRoles(roles);
        return registerNewUser(map);

    }

    @Override
    public boolean checkEmailAvailability(String email) {
        return _accountRepository.existsAccountByEmail(email);
    }

    @Override
    public Account findAccountByPersonId(UUID personId) {
        return _accountRepository.findAccountByPersonId(personId);
    }

    @Override
    public Account findAccountByEmail(String email) {
        return _accountRepository.findByEmail(email);
    }

    @Override
    public Account activateAccount(ActivateAccountDTO activateAccountDTO) {
        if (!_activateAccountService.existsByAccountIdAndAcctivationCode(activateAccountDTO.getAccountId(), activateAccountDTO.getActivationCode())) {
            throw new NotFoundException("No activation code for this account available");
        }
        Account account = findById(activateAccountDTO.getAccountId());
        if (account.isActivated()) {
            throw new IllegalArgumentException("Account already activated");
        }
        account.setActivated(true);
        return save(account);

    }


}