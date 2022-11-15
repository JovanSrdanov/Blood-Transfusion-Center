package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.AssignBloodCenterDTO;
import groupJASS.ISA_2022.DTO.BloodAdmin.BloodAdminProfileDTO;
import groupJASS.ISA_2022.DTO.BloodAdmin.BloodAdminRegistrationDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.BloodAdmin;
import groupJASS.ISA_2022.Service.Interfaces.IBloodAdminService;
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
@RequestMapping("blood-admin")
public class BloodAdminController {
    private final IBloodAdminService _bloodAdminService;
    private final ModelMapper _modelMapper;

    @Autowired
    public BloodAdminController(IBloodAdminService bloodAdminService, ModelMapper mapper) {
        _bloodAdminService = bloodAdminService;
        this._modelMapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<BloodAdmin>> findAll() {
        var res = (List<BloodAdmin>) _bloodAdminService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BloodAdmin> findById(@PathVariable UUID id) {

        try {
            var res = _bloodAdminService.findById(id);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/logged-in")
    public ResponseEntity<BloodAdmin> getLoggedInAdmin() {
        var res = (List<BloodAdmin>) _bloodAdminService.findAll();
        if (res.isEmpty()) {
            return null;
        } else {
            var test = res.get(0);
            //TEST
            var center = test.getBloodCenter();
            if (center != null) {
                center.setStaff(null);
                test.setBloodCenter(center);
            }
            return new ResponseEntity<>(test, HttpStatus.OK);
        }
    }

    @PutMapping(path = "updateBloodAdmin/{id}")
    ResponseEntity<?> updateAdmin(@PathVariable("id") String adminId, @Valid @RequestBody BloodAdminProfileDTO dto) {
        try {
            UUID id = UUID.fromString(adminId);
            BloodAdmin oldAdmin = _bloodAdminService.findById(id);
            BloodAdmin newAdmin = _modelMapper.map(dto, BloodAdmin.class);
            newAdmin.setId(id);
            //TEMP
            var tempCenter = oldAdmin.getBloodCenter();
            if (tempCenter != null) {
                tempCenter.setStaff(null);
            }
            //TEMP
            newAdmin.setBloodCenter(tempCenter);
            return new ResponseEntity<>(_bloodAdminService.save(newAdmin), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/save")
    public ResponseEntity<BloodAdmin> save(@RequestBody BloodAdmin admin) {
        BloodAdmin res;
        try {
            res = _bloodAdminService.save(admin);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> registerBloodAdmin(@RequestBody BloodAdminRegistrationDTO dto) {
        try {
            System.out.println("test");
            _bloodAdminService.register(dto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("That username already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping
    public ResponseEntity<String> assignBloodCenter(@RequestBody AssignBloodCenterDTO dto) {
        try {
            _bloodAdminService.assignBloodCenter(dto.getBloodAdminId(), dto.getBloodCenterId());
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
    public ResponseEntity<Iterable<BloodAdmin>> getUnemployedBloodAdmins() {
        Iterable<BloodAdmin> bloodAdmins = _bloodAdminService.getUnemployedBloodAdmins();
        return new ResponseEntity<>(bloodAdmins, HttpStatus.OK);
    }
}
