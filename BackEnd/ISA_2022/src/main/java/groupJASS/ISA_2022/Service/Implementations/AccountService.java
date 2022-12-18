package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Repository.AccountRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
@Primary
public class AccountService implements IAccountService {

    private final AccountRepository _accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleService _roleService;

    @Autowired
    public AccountService(AccountRepository accountRepository, IRoleService roleService, PasswordEncoder passwordEncoder1) {
        _accountRepository = accountRepository;
        _roleService = roleService;
        passwordEncoder = passwordEncoder1;
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
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        save(account);

    }

    @Override
    public void registerRegisteredUser(Account map, BloodDonor bloodDonor) {
        map.setPersonId(bloodDonor.getId());
        // Todo ispravi
        //map.setRole(Role.BLOOD_DONOR);
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


}