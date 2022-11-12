package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.BloodUser;
import groupJASS.ISA_2022.Repository.BloodUserRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodUserService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Primary
public class BloodUserService implements IBloodUserService {

    private final BloodUserRepository _blooBloodUserRepository;

    @Autowired
    public BloodUserService(BloodUserRepository bloodUserRepository)
    {
        _blooBloodUserRepository = bloodUserRepository;
    }

    @Override
    public Iterable<BloodUser> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public BloodUser findById(UUID id) {
        throw new NotImplementedException();
    }

    @Override
    public BloodUser save(BloodUser entity) {
        entity.setId(UUID.randomUUID());
        return  _blooBloodUserRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        throw new NotImplementedException();
    }
}
