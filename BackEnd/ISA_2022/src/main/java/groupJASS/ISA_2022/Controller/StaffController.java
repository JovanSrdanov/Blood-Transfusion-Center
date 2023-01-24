package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Account.PasswordDTO;
import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterProfileDto;
import groupJASS.ISA_2022.DTO.Staff.*;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.Staff;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAddressService;
import groupJASS.ISA_2022.Service.Interfaces.IStaffService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("staff")
public class StaffController {
    private final IStaffService _staffService;
    private final ModelMapper _mapper;
    private final IAccountService _accountService;
    private final IAddressService _addressService;

    @Autowired
    public StaffController(IStaffService staffService, ModelMapper mapper, IAccountService accountService,
                           IAddressService addressService) {
        _staffService = staffService;
        _mapper = mapper;
        _accountService = accountService;
        _addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Staff>> findAll() {
        var res = (List<Staff>) _staffService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Staff> findById(@PathVariable UUID id) {

        try {
            var res = _staffService.findById(id);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping(path = "/logged-in")
    public ResponseEntity getLoggedInStaff(Principal principal) {
        try{
            Staff staff = _staffService.findByEmail(principal);
            StaffProfileDTO dto = _mapper.map(staff, StaffProfileDTO.class);

            dto.setEmail(principal.getName());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (NotFoundException e)
        {
            return  new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "updateBloodAdmin/{id}")
    ResponseEntity<?> updateStaff(@PathVariable("id") UUID adminId, @Valid @RequestBody StaffProfileDTO dto) {
        try {
            //Staff oldAdmin = _staffService.findById(adminId);
            Staff staff = _mapper.map(dto, Staff.class);
            var address = staff.getAddress();
            _addressService.save(address);

            //newAdmin.setId(adminId);
            //var tempCenter = oldAdmin.getBloodCenter();
            //newAdmin.setBloodCenter(tempCenter);

            return new ResponseEntity<>(_staffService.save(staff), HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Staff> save(@RequestBody Staff staff) {
        Staff res;
        try {
            res = _staffService.save(staff);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    public ResponseEntity<String> registerStaff(@Valid @RequestBody StaffRegistrationDTO dto) {
        try {

            _staffService.register(dto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping
    public ResponseEntity<String> assignBloodCenter(@RequestBody AssignBloodCenterDTO dto) {
        try {
            _staffService.assignBloodCenter(dto.getStaffId(), dto.getBloodCenterId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "change-password/{id}")
    public ResponseEntity<?> chagePassword(@PathVariable("id") UUID id, @Valid @RequestBody PasswordDTO dto) {
        try {
            _staffService.changePassword(id, dto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    @GetMapping(path = "unemployed")
    public ResponseEntity<Iterable<StaffBasicInfoDTO>> getUnemployedStaff() {
        var dtos = (List<StaffBasicInfoDTO>) _staffService.getUnemployedStaff();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @GetMapping(path = "email-available/{email}")
    public ResponseEntity<Boolean> checkEmailAvailability(@PathVariable String email) {
        Boolean exists = _accountService.checkEmailAvailability(email);
        return new ResponseEntity<>(!exists, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping(path = "by-blood-center")
    public ResponseEntity<List<StaffFreeSlotsInfo>> getByBloodCenterId(Principal account) {
        Account acc = _accountService.findAccountByEmail(account.getName());
        Staff staff = _staffService.findById(acc.getPersonId());

        return new ResponseEntity<>(_staffService.getByBloodCenterId(staff.getBloodCenter().getId()), HttpStatus.OK);
    }

}
