package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.DTO.AddressDTO;
import groupJASS.ISA_2022.DTO.BloodAdmin.BloodAdminRegistrationDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.*;
import groupJASS.ISA_2022.Repository.BloodAdminRepository;
import groupJASS.ISA_2022.Repository.BloodCenterRepository;
import groupJASS.ISA_2022.Repository.BloodUserRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodAdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class BloodAdminService implements IBloodAdminService {

    private final BloodAdminRepository _bloodAdminRepository;
    private final BloodCenterRepository bloodCenterRepository;
    private final BloodUserRepository _bloodUserRepository;
    private final ModelMapper _mapper;

    @Autowired
    public BloodAdminService(BloodAdminRepository bloodAdminRepository, BloodCenterRepository bloodCenterService,
                             BloodUserRepository bloodUserRepository, ModelMapper mapper) {
        _bloodAdminRepository = bloodAdminRepository;
        bloodCenterRepository = bloodCenterService;
        _bloodUserRepository = bloodUserRepository;
        _mapper = mapper;
    }

    @Override
    public Iterable<BloodAdmin> findAll() {
        return _bloodAdminRepository.findAll();
    }

    @Override
    public BloodAdmin findById(UUID id) throws NotFoundException {
        if (_bloodAdminRepository.findById(id).isPresent()) {
            return _bloodAdminRepository.findById(id).get();
        }
        throw new NotFoundException("Blood admin not found");
    }

    @Override
    public BloodAdmin save(BloodAdmin entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        return _bloodAdminRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _bloodAdminRepository.deleteById(id);
    }

    @Override
    public void assignBloodCenter(UUID bloodAdminId, UUID bloodCenterId) throws BadRequestException {
        Optional<BloodAdmin> bloodAdmin = _bloodAdminRepository.findById(bloodAdminId);
        if (bloodAdmin.isEmpty()) {
            throw new NotFoundException("Blood admin not found");
        }

        if (bloodAdmin.get().getBloodCenter() != null) {
            throw new BadRequestException("Blood admin already has assigned blood center");
        }

        Optional<BloodCenter> bloodCenter = bloodCenterRepository.findById(bloodCenterId);
        if (bloodCenter.isEmpty()) {
            throw new NotFoundException("Blood center not found");
        }
        BloodAdmin b = bloodAdmin.get();
        b.setBloodCenter(bloodCenter.get());
        _bloodAdminRepository.save(b);
    }
    @Override
    public Iterable<BloodAdmin> getUnemployedBloodAdmins(){
       return _bloodAdminRepository.getUnemployedBloodAdmins();
    }

    @Override
    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public void register(BloodAdminRegistrationDTO dto) {

        Address address = _mapper.map(dto.getAddress(), Address.class);
        address.setId(UUID.randomUUID());

        BloodAdmin bloodAdmin = _mapper.map(dto, BloodAdmin.class);
        bloodAdmin.setId(UUID.randomUUID());
        bloodAdmin.setAddress(address);

        UUID bloodAdminId = _bloodAdminRepository.save(bloodAdmin).getId();

        BloodUser bloodUser = _mapper.map(dto, BloodUser.class);
        bloodUser.setRole(Role.MEDICAL_ADMIN);
        bloodUser.setPersonId(bloodAdminId);
        bloodUser.setId(UUID.randomUUID());
        _bloodUserRepository.save(bloodUser);
    }

    public boolean checkEmailAvailability(String email)
    {
        return _bloodAdminRepository.existsBloodAdminByEmail(email);
    }
}
