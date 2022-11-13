package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.BloodCenterRegistrationDTO;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("blood-center")
public class BloodCenterController {
    @GetMapping
    public ResponseEntity<List<BloodCenter>> findAll() {
        var res = (List<BloodCenter>) _service.findAll();
        return new ResponseEntity<List<BloodCenter>>(res, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BloodCenter> findById(@PathVariable("id") UUID id) {
        try {
            var center = _service.findById(id);
            return new ResponseEntity<>(center, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<BloodCenter> save(@RequestBody BloodCenter center) {
        var res = _service.save(center);
        return new ResponseEntity<>(res, HttpStatus.CREATED);

    private final IBloodCenterService _bloodCenterService;
    private final ModelMapper _mapper;

    @Autowired
    public BloodCenterController(IBloodCenterService bloodCenterService, ModelMapper mapper)
    {
       _bloodCenterService = bloodCenterService;
        _mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> registerBloodCenter(@RequestBody BloodCenterRegistrationDTO dto)
    {
       try{
           _bloodCenterService.save(_mapper.map(dto, BloodCenter.class));
           return new ResponseEntity<>(HttpStatus.CREATED);
       }
       catch (Exception e)
       {
           return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
}
