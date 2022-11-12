package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.RegisteredUser;
import groupJASS.ISA_2022.Repository.RegisteredUserRepository;
import groupJASS.ISA_2022.Service.Interfaces.IRegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class RegisteredUserService implements IRegisteredUserService {

    private final RegisteredUserRepository _registeredUserRepository;

    @Autowired
    public RegisteredUserService(RegisteredUserRepository registeredUserRepository) {
        _registeredUserRepository = registeredUserRepository;
    }

    @Override
    public List<RegisteredUser> findAll() {
        return _registeredUserRepository.findAll();
    }

    @Override
    public RegisteredUser findById(UUID id) {
        if (_registeredUserRepository.findById(id).isPresent()) {

            return _registeredUserRepository.findById(id).get();
        }

        throw new NotFoundException("Registered user not found");
    }

    @Override
    public RegisteredUser save(RegisteredUser entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        return _registeredUserRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _registeredUserRepository.deleteById(id);
    }
}
