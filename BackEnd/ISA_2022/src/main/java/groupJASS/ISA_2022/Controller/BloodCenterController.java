package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterBasicInfoDto;
import groupJASS.ISA_2022.DTO.BloodCenter.BloodCentarBasicInfoDto;
import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterRegistrationDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.ObjectMapperUtils;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import groupJASS.ISA_2022.Utilities.MappingUtilities;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping(path = "all-basic-info")
    public ResponseEntity<Iterable<BloodCentarBasicInfoDto>> findAllBasicInfo() {
        var bloodCenters = (List<BloodCenter>)   _bloodCenterService.findAll();
        var res = MappingUtilities.mapList(bloodCenters, BloodCentarBasicInfoDto.class, _mapper);
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
        catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/sort/{offset}/{size}/{field}/{s}")
    public List<BloodCenterBasicInfoDto> sortCenter(@PathVariable int offset, @PathVariable int size,
                                                    @PathVariable String field, @PathVariable String s) {

        Page<BloodCenter> entities = _bloodCenterService.findProductsWithSorting(offset, size, field, s);

        return ObjectMapperUtils.mapAll(entities.getContent(), BloodCenterBasicInfoDto.class);
    }
}
