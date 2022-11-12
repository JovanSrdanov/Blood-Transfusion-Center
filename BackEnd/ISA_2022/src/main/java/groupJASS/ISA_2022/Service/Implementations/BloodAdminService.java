package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.BloodAdmin;
import groupJASS.ISA_2022.Repository.BloodAdminRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
@Primary
public class BloodAdminService implements IBloodAdminService {
    private final BloodAdminRepository _repository;

    @Autowired
    public BloodAdminService(BloodAdminRepository repository) {
        this._repository = repository;
    }

    @Override
    public Iterable<BloodAdmin> findAll() {
        return _repository.findAll();
    }

    @Override
    public BloodAdmin findById(UUID id) throws NotFoundException {
        if (_repository.findById(id).isPresent()) {
            return _repository.findById(id).get();
        }
        throw new NotFoundException("Blood admin not found");
    }

    @Override
    public BloodAdmin save(BloodAdmin entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        return _repository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _repository.deleteById(id);
    }
}
