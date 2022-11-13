package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.AssignBloodCenterDTO;
import groupJASS.ISA_2022.DTO.BloodAdminRegistrationDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.BloodAdmin;
import groupJASS.ISA_2022.Service.Interfaces.IBloodAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("blood-admin")
public class BloodAdminController {
    private final IBloodAdminService _bloodAdminService;

    @Autowired
    public BloodAdminController(IBloodAdminService bloodAdminService) {
        _bloodAdminService = bloodAdminService;
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


    @PostMapping(consumes = "application/json" )
    public ResponseEntity<String> registerBloodAdmin(@RequestBody BloodAdminRegistrationDTO dto)
    {
        try{

            _bloodAdminService.register(dto);
            return  new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (DataIntegrityViolationException e)
        {
            return new ResponseEntity<>("That username already exists", HttpStatus.CONFLICT);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping
    public ResponseEntity<String> assignBloodCenter(@RequestBody AssignBloodCenterDTO dto)
    {
       try{
            _bloodAdminService.assignBloodCenter(dto.getBloodAdminId(), dto.getBloodCenterId());
           return  new ResponseEntity<>(HttpStatus.OK);
       }
       catch (NotFoundException e)
       {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
       }
       catch (BadRequestException e)
       {
           return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
       catch (Exception e)
       {
           return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping(path ="unemployed")
    public ResponseEntity<Iterable<BloodAdmin>> getUnemployedBloodAdmins(){
       Iterable<BloodAdmin>  bloodAdmins = _bloodAdminService.getUnemployedBloodAdmins();
       return new ResponseEntity<>(bloodAdmins, HttpStatus.OK);
    }
}
