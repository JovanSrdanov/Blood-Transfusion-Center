package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorInfoDto;
import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorLazyDTO;
import groupJASS.ISA_2022.DTO.BloodDonor.RegisterBloodDonorDTO;
import groupJASS.ISA_2022.DTO.BloodDonor.UpdateBloodDonorInfoDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Service.Interfaces.IAddressService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodDonorService;
import groupJASS.ISA_2022.Utilities.MappingUtilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("blood-donor")
public class BloodDonorController {

    private final IBloodDonorService _bloodDonorService;
    private final IAddressService _addressService;

    private final ModelMapper _mapper;

    @Autowired
    public BloodDonorController(IBloodDonorService bloodDonorService, ModelMapper modelMapper,
                                IAddressService addressService) {
        this._bloodDonorService = bloodDonorService;
        this._mapper = modelMapper;
        this._addressService = addressService;

    }

    @GetMapping
    public ResponseEntity<List<BloodDonor>> findAllWithAddressAndQuestionnaire() {
        return new ResponseEntity<>(this._bloodDonorService.findAllWithAddressAndQuestionnaire(), HttpStatus.OK);
    }

    @GetMapping("lazy")
    public ResponseEntity<List<BloodDonorLazyDTO>> findAllLazy() {
        return new ResponseEntity<>(MappingUtilities.mapList((List<BloodDonor>) this._bloodDonorService.findAll(), BloodDonorLazyDTO.class, _mapper), HttpStatus.OK);
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<BloodDonorInfoDto> findById(@PathVariable UUID id) {
        BloodDonorInfoDto userDto =
                _mapper.map(_bloodDonorService.findById(id), BloodDonorInfoDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BloodDonor> save(@RequestBody BloodDonor entity) {
        try {
            return new ResponseEntity<>(this._bloodDonorService.save(entity), HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("update")
    public ResponseEntity<BloodDonor> update(@Valid @RequestBody UpdateBloodDonorInfoDto updatedUserDto) {
        BloodDonor updatedUser = _mapper.map(updatedUserDto, BloodDonor.class);

        try {
            _addressService.saveAddresFromBloodDonorRegistration(_mapper.map(updatedUserDto.getAddress(), Address.class));
            return new ResponseEntity<>(this._bloodDonorService.save(updatedUser), HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> Register(@Valid @RequestBody RegisterBloodDonorDTO dto)
            throws ConstraintViolationException {

        try {
            _bloodDonorService.registerNewBloodDonor(dto);


            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("search/{name}/{surname}")
    public ResponseEntity<List<BloodDonorInfoDto>> searchByNameAndSurname(@PathVariable String name, @PathVariable String surname)
    {
        List<BloodDonorInfoDto> bloodDonors = (List<BloodDonorInfoDto>) _bloodDonorService.findBloodDonorByNameAAndSurname(name, surname);
        var res = MappingUtilities.mapList(bloodDonors, BloodDonorInfoDto.class, _mapper);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
