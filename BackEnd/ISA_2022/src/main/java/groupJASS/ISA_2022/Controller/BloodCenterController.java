package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterRegistrationDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("blood-center")
public class BloodCenterController {
    private final IBloodCenterService _bloodCenterService;


    private final ModelMapper _mapper;

    @Autowired
    public BloodCenterController(IBloodCenterService bloodCenterService, ModelMapper mapper) {
        _bloodCenterService = bloodCenterService;
        _mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<BloodCenter>> findAll() {
        var res = (List<BloodCenter>) _bloodCenterService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BloodCenter> findById(@PathVariable("id") UUID id) {
        try {
            var center = _bloodCenterService.findById(id);
            return new ResponseEntity<>(center, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/save")
    public ResponseEntity<BloodCenter> save(@RequestBody BloodCenter center) {
        BloodCenter res;
        try {
            res = _bloodCenterService.save(center);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<String> registerBloodCenter(@Valid @RequestBody BloodCenterRegistrationDTO dto) {
        try {
            _bloodCenterService.save(_mapper.map(dto, BloodCenter.class));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/sort/{offset}/{size}/{field}/{s}")
    public Page<BloodCenter> sortCenter(@PathVariable int offset, @PathVariable int size, @PathVariable String field, @PathVariable String s) {
       return _bloodCenterService.findProductsWithSorting(offset, size, field, s);
    }
}
