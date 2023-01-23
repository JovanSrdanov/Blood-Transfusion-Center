package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterBasicInfoDto;
import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterRegistrationDTO;
import groupJASS.ISA_2022.DTO.BloodCenter.WorkingHoursRoundedDto;
import groupJASS.ISA_2022.DTO.PageEntityDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Exceptions.BloodCenterNotAssignedException;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import groupJASS.ISA_2022.Service.Interfaces.IStaffService;
import groupJASS.ISA_2022.Utilities.MappingUtilities;
import groupJASS.ISA_2022.Utilities.ObjectMapperUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
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
@RequestMapping("blood-center")
public class BloodCenterController {
    private final IBloodCenterService _bloodCenterService;

    private final IAccountService _accountService;
    private final IStaffService _staffService;

    private final ModelMapper _mapper;

    @Autowired
    public BloodCenterController(IBloodCenterService bloodCenterService, ModelMapper mapper, IAccountService accountService, IStaffService staffService) {
        _bloodCenterService = bloodCenterService;
        _mapper = mapper;
        _accountService = accountService;
        _staffService = staffService;
    }

    @GetMapping
    public ResponseEntity<List<BloodCenter>> findAll() {
        var res = (List<BloodCenter>) _bloodCenterService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(path = "all-basic-info")
    public ResponseEntity<Iterable<BloodCenterBasicInfoDto>> findAllBasicInfo() {
        var bloodCenters = (List<BloodCenter>) _bloodCenterService.findAll();
        var res = MappingUtilities.mapList(bloodCenters, BloodCenterBasicInfoDto.class, _mapper);
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

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping("working-hours-rounded")
    public ResponseEntity getRoundedWorkingHours(Principal principal) {
        try {
            WorkingHoursRoundedDto workingHoursRoundedDto = _bloodCenterService.getRoundedWorkingHours(principal);
            return new ResponseEntity<WorkingHoursRoundedDto>(workingHoursRoundedDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BloodCenterNotAssignedException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping("/incoming-appointments")
    public ResponseEntity getIncomingAppointments(Principal principal) {
        try {
            var result = _bloodCenterService.getIncomingAppointments(principal);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BloodCenterNotAssignedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateCenter(@PathVariable("id") UUID centerId, @Valid @RequestBody BloodCenterRegistrationDTO dto) {
        try {
            //UUID id = UUID.fromString(centerId);
            BloodCenter oldCenter = _bloodCenterService.findById(centerId);
            BloodCenter newCenter = _mapper.map(dto, BloodCenter.class);
            newCenter.setId(centerId);
            newCenter.getAddress().setId((oldCenter.getAddress().getId()));
            newCenter.setWorkingHours(oldCenter.getWorkingHours());
            newCenter.setStaff(oldCenter.getStaff());
            newCenter.setAppointments(oldCenter.getAppointments());
            newCenter.setBloodQuantities(oldCenter.getBloodQuantities());

            return new ResponseEntity<>(_bloodCenterService.save(newCenter), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    public ResponseEntity<String> registerBloodCenter(@Valid @RequestBody BloodCenterRegistrationDTO dto) {
        try {
            _bloodCenterService.save(_mapper.map(dto, BloodCenter.class));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "sort")
    public ResponseEntity<?> sortCenter(@RequestParam(name = "page") int page,
                                        @RequestParam(name = "field") String field,
                                        @RequestParam(name = "sort") String sort,
                                        @RequestParam(name = "s") String s) {
        int size = 5;
        try {
            Page<BloodCenter> entities = _bloodCenterService.findProductsWithSorting(page, size, field, sort, s.trim());
            List<BloodCenterBasicInfoDto> content = ObjectMapperUtils.mapAll(entities.getContent(), BloodCenterBasicInfoDto.class);
            PageEntityDto<List<BloodCenterBasicInfoDto>> pageDto = new PageEntityDto<>(content, (int) entities.getTotalElements());
            return new ResponseEntity<>(pageDto, HttpStatus.OK);
        } catch (SortNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("call-the-helicopter")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity<?> callTheHelicopter(Principal account) {
        try {
            BloodCenter bloodCenter = _staffService.findById(_accountService.findAccountByEmail(account.getName()).getPersonId()).getBloodCenter();
            if (bloodCenter == null) {
                return new ResponseEntity<>("This staff member is not a member of any bloodcenter", HttpStatus.CONFLICT);
            }
            _bloodCenterService.callTheHelicopter(bloodCenter);


            return new ResponseEntity<>("Succes", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
