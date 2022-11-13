package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.BloodAdmin;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Repository.BloodAdminRepository;
import groupJASS.ISA_2022.Repository.BloodCenterRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class BloodAdminService implements IBloodAdminService {

    private final BloodAdminRepository _adminRepository;
    private final BloodCenterRepository _centerRepository;

    @Autowired
    public BloodAdminService(BloodAdminRepository adminRepository, BloodCenterRepository centerRepository) {
        _adminRepository = adminRepository;
        _centerRepository = centerRepository;
    }

    @Override
    public Iterable<BloodAdmin> findAll() {
        return _adminRepository.findAll();
    }

    @Override
    public BloodAdmin findById(UUID id) throws NotFoundException {
        if (_adminRepository.findById(id).isPresent()) {
            return _adminRepository.findById(id).get();
        }
        throw new NotFoundException("Blood admin not found");
    }

    @Override
    public BloodAdmin save(BloodAdmin entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        return _adminRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _adminRepository.deleteById(id);
    }

    public void assignBloodCenter(UUID bloodAdminId, UUID bloodCenterId) throws NotFoundException {
        Optional<BloodAdmin> bloodAdmin = _adminRepository.findById(bloodAdminId);
        if (bloodAdmin.isEmpty()) {
            throw new NotFoundException("Blood admin not found");
        }

        Optional<BloodCenter> bloodCenter = _centerRepository.findById(bloodCenterId);
        if (bloodCenter.isEmpty()) {
            throw new NotFoundException("Blood center not found");
        }
        BloodAdmin b = bloodAdmin.get();
        b.setBloodCenter(bloodCenter.get());
        _adminRepository.save(b);
    }
}
