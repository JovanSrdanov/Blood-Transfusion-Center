package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.Model.BloodAdmin;
import groupJASS.ISA_2022.Service.Implementations.BloodAdminService;
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
    private final BloodAdminService _service;
    private final ModelMapper _mapper;

    @Autowired
    public BloodAdminController(BloodAdminService service, ModelMapper mapper) {
        this._service = service;
        this._mapper = mapper;
    }

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
    }
}
