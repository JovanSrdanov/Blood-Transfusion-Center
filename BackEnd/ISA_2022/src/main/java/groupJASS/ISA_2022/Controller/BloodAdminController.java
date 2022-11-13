package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.AssignBloodCenterDTO;
import groupJASS.ISA_2022.DTO.BloodAdminRegistrationDTO;
import groupJASS.ISA_2022.Model.BloodAdmin;
import groupJASS.ISA_2022.Model.BloodUser;
import groupJASS.ISA_2022.Model.Role;
import groupJASS.ISA_2022.Service.Interfaces.IBloodAdminService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("blood-admin")
public class BloodAdminController {
    @GetMapping
    public ResponseEntity<List<BloodAdmin>> findAll() {
        var res = (List<BloodAdmin>) _service.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BloodAdmin> findById(@PathVariable UUID id) {

        try {
            var res = _service.findById(id);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<BloodAdmin> save(@RequestBody BloodAdmin admin) {
        var res = _service.save(admin);
        return new ResponseEntity<>(res, HttpStatus.CREATED);

    private final IBloodUserService _bloodUserService;
    private final ModelMapper _mapper;
    private final IBloodAdminService _bloodAdminService;

    @Autowired
    public BloodAdminController(IBloodUserService bloodUserService, ModelMapper modelMapper, IBloodAdminService bloodAdminService)
    {
        _bloodUserService = bloodUserService;
        _mapper = modelMapper;
        _bloodAdminService = bloodAdminService;
    }

    @PostMapping(consumes = "application/json" )
    public ResponseEntity<Void> registerBloodAdmin(@RequestBody BloodAdminRegistrationDTO dto)
    {
        try{
            //TODO: Convert this to transaction
            BloodAdmin bloodAdmin = _mapper.map(dto, BloodAdmin.class);
            UUID bloodAdminId = _bloodAdminService.save(bloodAdmin).getId();

            BloodUser bloodUser = _mapper.map(dto, BloodUser.class);
            bloodUser.setRole(Role.MEDICAL_ADMIN);
            bloodUser.setPersonId(bloodAdminId);

            _bloodUserService.save(bloodUser);

            return  new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping
    public ResponseEntity assignBloodCenter(@RequestBody AssignBloodCenterDTO dto)
    {
       try{
            _bloodAdminService.assignBloodCenter(dto.getBloodAdminId(), dto.getBloodCenterId());
           return  new ResponseEntity<>(HttpStatus.OK);
       }
       catch (NotFoundException e)
       {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
       }
       catch (Exception e)
       {
           return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
}
