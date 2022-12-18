package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Model.Role;
import groupJASS.ISA_2022.Repository.AccountRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
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

    @Autowired
    private PasswordEncoder _passwordEncoder;

    @Autowired
    private IRoleService _roleService;

    @Autowired
    public AccountService(AccountRepository accountRepository, IRoleService roleService, PasswordEncoder passwordEncoder) {
        _accountRepository = accountRepository;
        _roleService = roleService;
        this._passwordEncoder = passwordEncoder;
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
    public void registerNewUser(Account account) {


        if (_accountRepository.existsAccountByEmail(account.getEmail())) {

            throw new IllegalArgumentException("Account with this email already exists");
        }
        account.setActivated(false);
        account.setPassword(_passwordEncoder.encode(account.getPassword()));
        save(account);

    }

    @Override
    public void registerRegisteredUser(Account map, BloodDonor bloodDonor) {
        map.setPersonId(bloodDonor.getId());
        List<Role> roles = _roleService.findByName("ROLE_BLOOD_DONOR");
        map.setRoles(roles);
        registerNewUser(map);

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


}