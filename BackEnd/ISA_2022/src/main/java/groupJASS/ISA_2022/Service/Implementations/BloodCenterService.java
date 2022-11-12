package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Repository.BloodCenterRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
@Primary
public class BloodCenterService implements IBloodCenterService {
    private final BloodCenterRepository _repository;

    @Autowired
    public BloodCenterService(BloodCenterRepository repository) {
        this._repository = repository;
    }

    @Override
    public Iterable<BloodCenter> findAll() {
        return _repository.findAll();
    }

    @Override
    public BloodCenter findById(UUID id) throws NotFoundException {
        if (_repository.findById(id).isPresent()) {
            return _repository.findById(id).get();
        }
        throw new NotFoundException("Blood center not found");
    }

    @Override
    public BloodCenter save(BloodCenter entity) {
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
