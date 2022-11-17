package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorInfoDto;
import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorLazyDTO;
import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorSearchByNameAndSurnameDto;
import groupJASS.ISA_2022.DTO.BloodDonor.RegisterBloodDonorDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAddressService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodDonorService;
import groupJASS.ISA_2022.Utilities.MappingUtilities;
import groupJASS.ISA_2022.Utilities.ObjectMapperUtils;
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

    private final IAccountService _accountService;

    private final ModelMapper _mapper;

    @Autowired
    public BloodDonorController(IBloodDonorService bloodDonorService, ModelMapper modelMapper,
                                IAddressService addressService, IAccountService _accountService) {
        this._bloodDonorService = bloodDonorService;
        this._mapper = modelMapper;
        this._addressService = addressService;
        this._accountService = _accountService;

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
        Account acc = _accountService.findAccountByPersonId(id);
        BloodDonorInfoDto userDto = new BloodDonorInfoDto(_bloodDonorService.findById(id), acc.getEmail());
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
    public ResponseEntity<BloodDonorInfoDto> update(@Valid @RequestBody BloodDonorInfoDto updatedUserDto) {
        BloodDonor updatedDonor = _mapper.map(updatedUserDto, BloodDonor.class);

        try {
            BloodDonor entity = _bloodDonorService.updateDonorInfo(
                    ObjectMapperUtils.map(updatedUserDto.getAddress(), Address.class), updatedDonor);
            Account acc = _accountService.findAccountByPersonId(entity.getId());
            BloodDonorInfoDto dto = new BloodDonorInfoDto(entity, acc.getEmail());
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
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

    //It is not get because you cant send null parameter inside path variable, and I need that case
    @PostMapping("search-name-surname")
    public ResponseEntity<List<BloodDonorInfoDto>> searchByNameAndSurname(@RequestBody BloodDonorSearchByNameAndSurnameDto dto)
    {
        List<BloodDonorInfoDto> bloodDonors = (List<BloodDonorInfoDto>) _bloodDonorService.findBloodDonorByNameAAndSurname(dto.getName(), dto.getSurname());
        var res = MappingUtilities.mapList(bloodDonors, BloodDonorInfoDto.class, _mapper);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
