package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Repository.BloodCenterRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
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
        return _bloodCenterRepository.findAll();
    }

    @Override
    public BloodCenter findById(UUID id) throws NotFoundException {
        if (_bloodCenterRepository.findById(id).isPresent()) {
            return _bloodCenterRepository.findById(id).get();
        }
        throw new NotFoundException("Blood center not found");
    }

    @Override
    public BloodCenter save(BloodCenter entity) {
        if (entity.getId() == null) {
            entity.getAddress().setId(UUID.randomUUID());
            entity.setId(UUID.randomUUID());
        }

        return _bloodCenterRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _bloodCenterRepository.deleteById(id);
    }

    public List<BloodCenter> findProductsWithSorting(String field){
        return  _bloodCenterRepository.findAll(Sort.by(Sort.Direction.ASC,field));
    }
}
