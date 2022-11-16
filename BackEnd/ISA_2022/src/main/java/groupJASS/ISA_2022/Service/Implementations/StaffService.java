package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.DTO.Account.AccountDTO;
import groupJASS.ISA_2022.DTO.Account.PasswordDTO;
import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterProfileDto;
import groupJASS.ISA_2022.DTO.Staff.StaffBasicInfoDTO;
import groupJASS.ISA_2022.DTO.Staff.StaffProfileDTO;
import groupJASS.ISA_2022.DTO.Staff.StaffRegistrationDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.*;
import groupJASS.ISA_2022.Repository.AccountRepository;
import groupJASS.ISA_2022.Repository.BloodCenterRepository;
import groupJASS.ISA_2022.Repository.StaffRepository;
import groupJASS.ISA_2022.Service.Interfaces.IStaffService;
import groupJASS.ISA_2022.Utilities.MappingUtilities;
import org.apache.commons.lang3.NotImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
public class StaffService implements IStaffService {

    private final StaffRepository _staffRepository;
    private final BloodCenterRepository _bloodCenterRepository;
    private final AccountRepository _accountRepository;
    private final ModelMapper _mapper;

    @Autowired
    public StaffService(StaffRepository staffRepository, BloodCenterRepository bloodCenterService,
                        AccountRepository accountRepository, ModelMapper mapper) {
        _staffRepository = staffRepository;
        _bloodCenterRepository = bloodCenterService;
        _accountRepository = accountRepository;
        _mapper = mapper;
    }

    @Override
    public Iterable<Staff> findAll() {
        return _staffRepository.findAll();
    }

    public List<StaffProfileDTO> getAllStaff() {
        List<Staff> staff = _staffRepository.findAll();
        return getStaffProfileInfo(staff);
    }

    @Override
    public void changePassword(UUID id, PasswordDTO dto) {
        Account account = _accountRepository.findAccountByPersonId(id);
        if (account == null) {
            throw new NotFoundException("Account with that id not found");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        account.setPassword(dto.getNewPassword());
        _accountRepository.save(account);
    }

    @Override
    public List<StaffBasicInfoDTO> getUnemployedStaff() {
        List<Staff> staff = (List<Staff>) _staffRepository.getUnemployedStaff();
        //Getting emails from account
        return getStaffBasicInfo(staff);
    }

    private List<StaffProfileDTO> getStaffProfileInfo(List<Staff> staff) {
        var dtos = MappingUtilities.mapList(staff, StaffProfileDTO.class, _mapper);

        dtos.stream().map(dto ->
        {
            Account account = _accountRepository.findAccountByPersonId(dto.getId());
            dto.setEmail(account.getEmail());
            return dto;
        }).collect(Collectors.toList());

        return dtos;
    }

    private List<StaffBasicInfoDTO> getStaffBasicInfo(List<Staff> staff) {

        var dtos = MappingUtilities.mapList(staff, StaffBasicInfoDTO.class, _mapper);

        dtos.stream().map(dto ->
        {
            Account account = _accountRepository.findAccountByPersonId(dto.getId());
            dto.setEmail(account.getEmail());
            return dto;
        }).collect(Collectors.toList());

        return dtos;
    }

    @Override
    public Staff findById(UUID id) throws NotFoundException {
        if (_staffRepository.findById(id).isPresent()) {
            return _staffRepository.findById(id).get();
        }
        throw new NotFoundException("Blood admin not found");
    }

    @Override
    public Staff save(Staff entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        return _staffRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _staffRepository.deleteById(id);
    }

    @Override
    public void assignBloodCenter(UUID bloodAdminId, UUID bloodCenterId) throws BadRequestException {
        Optional<Staff> bloodAdmin = _staffRepository.findById(bloodAdminId);
        if (bloodAdmin.isEmpty()) {
            throw new NotFoundException("Blood admin not found");
        }

        if (bloodAdmin.get().getBloodCenter() != null) {
            throw new BadRequestException("Blood admin already has assigned blood center");
        }

        Optional<BloodCenter> bloodCenter = _bloodCenterRepository.findById(bloodCenterId);
        if (bloodCenter.isEmpty()) {
            throw new NotFoundException("Blood center not found");
        }
        Staff b = bloodAdmin.get();
        b.setBloodCenter(bloodCenter.get());
        _staffRepository.save(b);
    }

    @Override
    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public void register(StaffRegistrationDTO dto) {

        Address address = _mapper.map(dto.getAddress(), Address.class);
        address.setId(UUID.randomUUID());

        Staff staff = _mapper.map(dto, Staff.class);
        staff.setId(UUID.randomUUID());
        staff.setAddress(address);

        UUID stafId = _staffRepository.save(staff).getId();

        Account account = _mapper.map(dto, Account.class);
        account.setRole(Role.STAFF);
        account.setPersonId(stafId);
        account.setId(UUID.randomUUID());
        _accountRepository.save(account);
    }
}
