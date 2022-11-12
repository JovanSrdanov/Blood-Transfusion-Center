package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.BloodAdmin;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Repository.BloodAdminRepository;
import groupJASS.ISA_2022.Repository.BloodCenterRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodAdminService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class BloodAdminService implements IBloodAdminService {

    private final BloodAdminRepository _bloodAdminRepository;
    private final BloodCenterRepository bloodCenterRepository;

    @Autowired
    public BloodAdminService(BloodAdminRepository bloodAdminRepository, BloodCenterRepository bloodCenterService)
    {
        _bloodAdminRepository = bloodAdminRepository;
        bloodCenterRepository = bloodCenterService;
    }
    @Override
    public Iterable<BloodAdmin> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public BloodAdmin findById(UUID id) {
        throw new NotImplementedException();
    }

    @Override
    public BloodAdmin save(BloodAdmin entity) {
        if(entity.getId() == null)
        {
            entity.setId(UUID.randomUUID());
        }
        return _bloodAdminRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        throw new NotImplementedException();
    }

    public void assignBloodCenter(UUID bloodAdminId, UUID bloodCenterId)
    {
        Optional<BloodAdmin> bloodAdmin = _bloodAdminRepository.findById(bloodAdminId);
        if(bloodAdmin.isEmpty())
        {
            throw new NotFoundException("Blood admin not found");
        }

        Optional<BloodCenter> bloodCenter = bloodCenterRepository.findById(bloodCenterId);
        if(bloodCenter.isEmpty())
        {
            throw new NotFoundException("Blood center not found");
        }
        BloodAdmin b =  bloodAdmin.get();
        b.setBloodCenter(bloodCenter.get());
        _bloodAdminRepository.save(b);
    }
}
