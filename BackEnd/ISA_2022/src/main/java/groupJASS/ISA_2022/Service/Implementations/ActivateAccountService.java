package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.ActivateAccount;
import groupJASS.ISA_2022.Repository.ActivateAccountRepository;
import groupJASS.ISA_2022.Service.Interfaces.IActivateAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
@Primary
public class ActivateAccountService implements IActivateAccountService {

    private final ActivateAccountRepository _activateAccountRepository;

    @Autowired
    ActivateAccountService(ActivateAccountRepository activateAccountRepository) {
        _activateAccountRepository = activateAccountRepository;
    }

    @Override
    public Iterable<ActivateAccount> findAll() {
        return _activateAccountRepository.findAll();
    }

    @Override
    public ActivateAccount findById(UUID id) {
        if (_activateAccountRepository.findById(id).isPresent()) {

            return _activateAccountRepository.findById(id).get();
        }

        throw new NotFoundException("Address not found");
    }

    @Override
    public ActivateAccount save(ActivateAccount entity) throws BadRequestException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        return _activateAccountRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _activateAccountRepository.deleteById(id);
    }


    @Override
    public boolean existsByAccountIdAndAcctivationCode(UUID AccountId, UUID AcctivationCode) {
        return _activateAccountRepository.existsActivateAccountByAccountIdAndActivationCode(AccountId, AcctivationCode);

    }
}
