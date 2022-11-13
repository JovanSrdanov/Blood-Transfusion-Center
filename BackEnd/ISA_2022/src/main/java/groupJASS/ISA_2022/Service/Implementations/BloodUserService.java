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

    private final BloodUserRepository _blooBloodUserRepository;

    @Autowired
    public BloodUserService(BloodUserRepository bloodUserRepository) {
        _blooBloodUserRepository = bloodUserRepository;
    }

    @Override
    public Iterable<BloodUser> findAll() {
        return _blooBloodUserRepository.findAll();
    }

    @Override
    public BloodUser findById(UUID id) {
        if (_blooBloodUserRepository.findById(id).isPresent()) {

            return _blooBloodUserRepository.findById(id).get();
        }

        throw new NotFoundException("User not found");
    }

    @Override
    public BloodUser save(BloodUser entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        return _blooBloodUserRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _blooBloodUserRepository.deleteById(id);
    }

    @Override
    public void registerNewUser(BloodUser bloodUser) {

        if (_blooBloodUserRepository.existsBloodUserByUsername(bloodUser.getUsername())) {

            throw new IllegalArgumentException("User with this username already exists");
        }

        save(bloodUser);

    }

    @Override
    public void registerRegisteredUser(BloodUser map, RegisteredUser registeredUser) {
        map.setPersonId(registeredUser.getId());
        map.setRole(Role.REGISTERED_USER);
        registerNewUser(map);

    }
}