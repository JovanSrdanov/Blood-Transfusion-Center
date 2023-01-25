package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.DTO.Account.AccountDTO;
import groupJASS.ISA_2022.DTO.Account.ActivateAccountDTO;
import groupJASS.ISA_2022.Model.Account;
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

import java.sql.Timestamp;
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
        _passwordEncoder = passwordEncoder;
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
    public void updatePassword(String email, String newPassword) {
        String hashedNewPassword = _passwordEncoder.encode(newPassword);
        _accountRepository.updatePassword(email, hashedNewPassword, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public Account save(Account entity) {
        return _accountRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _accountRepository.deleteById(id);
    }

    //At this moment one account only can have one role
    //For more roles this method must be refactored
    @Override
    public Account registerAccount(AccountDTO accountDto, String roleName, UUID personId) {
        List<Role> roles = _roleService.findByName(roleName);

        boolean isActivated = !roleName.equals("ROLE_BLOOD_DONOR");

        if (_accountRepository.existsAccountByEmail(accountDto.getEmail())) {

            throw new IllegalArgumentException("Account with this email already exists");
        }

        String hashedPassword = _passwordEncoder.encode(accountDto.getPassword());
        Account account = new Account(accountDto.getEmail(), hashedPassword, roles, personId, isActivated);
        return save(account);
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
        System.out.println("FIND WITHOUTH CACHE");
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