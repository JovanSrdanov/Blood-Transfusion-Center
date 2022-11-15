package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.BloodUser;
import groupJASS.ISA_2022.Model.RegisteredUser;
import groupJASS.ISA_2022.Model.Role;
import groupJASS.ISA_2022.Repository.BloodUserRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
@Primary
public class BloodUserService implements IBloodUserService {

    private final BloodUserRepository _bloodUserRepository;

    @Autowired
    public BloodUserService(BloodUserRepository bloodUserRepository) {
        _bloodUserRepository = bloodUserRepository;
    }

    @Override
    public Iterable<BloodUser> findAll() {
        return _bloodUserRepository.findAll();
    }

    @Override
    public BloodUser findById(UUID id) {
        if (_bloodUserRepository.findById(id).isPresent()) {

            return _bloodUserRepository.findById(id).get();
        }

        throw new NotFoundException("User not found");
    }

    @Override
    public BloodUser save(BloodUser entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        return _bloodUserRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _bloodUserRepository.deleteById(id);
    }

    @Override
    public void registerNewUser(BloodUser bloodUser) {
// Todo: email
        
//        if (_bloodUserRepository.existsBloodUserByUsername()) {
//
//            throw new IllegalArgumentException("User with this username already exists");
//        }

        save(bloodUser);

    }

    @Override
    public void registerRegisteredUser(BloodUser map, RegisteredUser registeredUser) {
        map.setPersonId(registeredUser.getId());
        map.setRole(Role.REGISTERED_USER);
        registerNewUser(map);

    }

    @Override
    public boolean checkUsernameAvailability(String username) {
        return _bloodUserRepository.existsBloodUserByUsername(username);
    }
}