package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Staff.AssignBloodCenterDTO;
import groupJASS.ISA_2022.DTO.Staff.StaffBasicInfoDTO;
import groupJASS.ISA_2022.DTO.Staff.StaffProfileDTO;
import groupJASS.ISA_2022.DTO.Staff.StaffRegistrationDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Staff;
import groupJASS.ISA_2022.Service.Interfaces.IStaffService;
import groupJASS.ISA_2022.Utilities.MappingUtilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("staff")
public class StaffController {
    private final IStaffService _staffService;
    private final ModelMapper _mapper;

    @Autowired
    public StaffController(IStaffService staffService, ModelMapper mapper) {
        _staffService = staffService;
        _mapper = mapper;
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

    @GetMapping(path = "/logged-in")
    public ResponseEntity<Staff> getLoggedInAdmin() {
        var res = (List<Staff>) _staffService.findAll();
        if (res.isEmpty()) {
            return null;
        } else {
            var admin = res.get(0);
            var center = admin.getBloodCenter();
            admin.setBloodCenter(center);
            return new ResponseEntity<>(res.get(0), HttpStatus.OK);
        }
    }

    @PutMapping(path = "updateBloodAdmin/{id}")
    ResponseEntity<?> updateAdmin(@PathVariable("id") UUID adminId, @Valid @RequestBody StaffProfileDTO dto) {
        try {
            //UUID id = UUID.fromString(adminId);
            Staff oldAdmin = _staffService.findById(adminId);
            Staff newAdmin = _mapper.map(dto, Staff.class);

            newAdmin.setId(adminId);
            var tempCenter = oldAdmin.getBloodCenter();
            newAdmin.setBloodCenter(tempCenter);

            return new ResponseEntity<>(_staffService.save(newAdmin), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Staff> save(@RequestBody Staff admin) {
        Staff res;
        try {
            res = _staffService.save(admin);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> registerStaff(@Valid @RequestBody StaffRegistrationDTO dto) {
        try {

            _staffService.register(dto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    @GetMapping(path = "unemployed")
    public ResponseEntity<Iterable<StaffBasicInfoDTO>> getUnemployedBloodAdmins() {
        var bloodAdmins = (List<Staff>) _staffService.getUnemployedBloodAdmins();
        var res = MappingUtilities.mapList(bloodAdmins, StaffBasicInfoDTO.class, _mapper);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @GetMapping(path = "email-available/{email}")
    public ResponseEntity<Boolean> checkEmailAvailability(@PathVariable String email) {
        Boolean exists = _staffService.checkEmailAvailability(email);
        return new ResponseEntity<>(!exists, HttpStatus.OK);
    }

}
