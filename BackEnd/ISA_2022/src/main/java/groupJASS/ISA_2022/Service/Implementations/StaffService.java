package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.DTO.Account.AccountDTO;
import groupJASS.ISA_2022.DTO.Account.PasswordDTO;
import groupJASS.ISA_2022.DTO.Staff.StaffBasicInfoDTO;
import groupJASS.ISA_2022.DTO.Staff.StaffFreeSlotsInfo;
import groupJASS.ISA_2022.DTO.Staff.StaffProfileDTO;
import groupJASS.ISA_2022.DTO.Staff.StaffRegistrationDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Model.Staff;
import groupJASS.ISA_2022.Repository.AccountRepository;
import groupJASS.ISA_2022.Repository.BloodCenterRepository;
import groupJASS.ISA_2022.Repository.StaffRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IRoleService;
import groupJASS.ISA_2022.Service.Interfaces.IStaffService;
import groupJASS.ISA_2022.Utilities.MappingUtilities;
import groupJASS.ISA_2022.Utilities.ObjectMapperUtils;
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
    private final ModelMapper _mapper;
    private final IRoleService _roleService;
    private final IAccountService _accountService;
    private final AccountRepository _accountRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository, BloodCenterRepository bloodCenterService,
                        AccountRepository accountRepository, ModelMapper mapper, IRoleService roleService, IAccountService accountService) {
        _staffRepository = staffRepository;
        _bloodCenterRepository = bloodCenterService;
        _mapper = mapper;
        _roleService = roleService;
        _accountService = accountService;
        _accountRepository = accountRepository;
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
        _accountService.updatePassword(account.getEmail(), dto.getNewPassword());
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

        List<StaffBasicInfoDTO> dtos = attachEmail(staff);
        return dtos;
    }

    private List<StaffBasicInfoDTO> attachEmail(List<Staff> staff) {
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
    public Staff findByEmail(UUID staffId) throws NotFoundException {



        Optional<Staff> staff = _staffRepository.findById(staffId);
        if(!staff.isPresent())
        {
            throw new NotFoundException("Staff not found");
        }

        System.out.println("--Find staff without cache-- " + staff.get().getName());

        return staff.get();

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
    @Transactional(rollbackFor = Exception.class)
    public void assignBloodCenter(UUID bloodAdminId, UUID bloodCenterId) throws BadRequestException {
        Optional<Staff> staff = _staffRepository.findById(bloodAdminId);
        if (staff.isEmpty()) {
            throw new NotFoundException("Staff not found");
        }

        if (staff.get().getBloodCenter() != null) {
            throw new BadRequestException("Staff already has assigned blood center");
        }

        Optional<BloodCenter> bloodCenter = _bloodCenterRepository.findById(bloodCenterId);
        if (bloodCenter.isEmpty()) {
            throw new NotFoundException("Blood center not found");
        }
        Staff b = staff.get();
        b.setBloodCenter(bloodCenter.get());

        try {
            _staffRepository.save(b);
        }
        catch (Exception e) {
            throw new BadRequestException("Neko je vec promenio ovog staff-a (optimistic lock)");
        }

    }

    @Override
    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public List<StaffBasicInfoDTO> getUnemployedStaff() {
        List<Staff> staff = (List<Staff>) _staffRepository.getUnemployedStaff();
        //Getting emails from account
        List<StaffBasicInfoDTO> dtos = attachEmail(staff);
        return dtos;
    }

    @Override
    public void register(StaffRegistrationDTO dto) {

        //TODO make constructor
        Address address = _mapper.map(dto.getAddress(), Address.class);
        address.setId(UUID.randomUUID());

        Staff staff = _mapper.map(dto, Staff.class);
        staff.setId(UUID.randomUUID());
        staff.setAddress(address);

        UUID stafId = _staffRepository.save(staff).getId();
        //TODO refactor StaffRegistrationDTO to contain AccountDto
        AccountDTO accountDto = new AccountDTO(dto.getEmail(), dto.getPassword());
        _accountService.registerAccount(accountDto, "ROLE_STAFF", staff.getId());
    }

    @Override
    public List<StaffFreeSlotsInfo> getByBloodCenterId(UUID bloodCenterId) {
        return ObjectMapperUtils.mapAll(_staffRepository.getByBloodCenterId(bloodCenterId), StaffFreeSlotsInfo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Staff updateStaff(Staff updatedStaff, UUID staffId) {
        Staff oldStaff = this.findById(staffId);

        oldStaff.update(updatedStaff);
        save(oldStaff);
        return oldStaff;
    }
}
