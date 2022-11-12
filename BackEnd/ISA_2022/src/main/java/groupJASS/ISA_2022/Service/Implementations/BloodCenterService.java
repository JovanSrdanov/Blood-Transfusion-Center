package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Repository.BloodCenterRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Primary
public class BloodCenterService implements IBloodCenterService {

    private final BloodCenterRepository _bloodCenterRepository;

    @Autowired
    public BloodCenterService(BloodCenterRepository bloodCenterRepository)
    {
        _bloodCenterRepository = bloodCenterRepository;
    }
    @Override
    public Iterable<BloodCenter> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public BloodCenter findById(UUID id) {
        throw new NotImplementedException();
    }

    @Override
    public BloodCenter save(BloodCenter entity) {
        entity.getAddress().setId(UUID.randomUUID());
        entity.setId(UUID.randomUUID());
        return _bloodCenterRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        throw new NotImplementedException();
    }
}
