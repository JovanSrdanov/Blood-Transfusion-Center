package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorInfoDto;
import groupJASS.ISA_2022.DTO.BloodDonor.RegisterBloodDonorDTO;
import groupJASS.ISA_2022.DTO.BloodDonor.UpdateBloodDonorInfoDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAddressService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodDonorService;
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
                                IAddressService addressService, IAccountService bloodUserService) {
        this._bloodDonorService = bloodDonorService;
        this._mapper = modelMapper;
        this._addressService = addressService;
        this._accountService = bloodUserService;
    }

    @GetMapping
    public ResponseEntity<List<BloodDonor>> findAll() {
        return new ResponseEntity<>((List<BloodDonor>) this._bloodDonorService.findAll(), HttpStatus.OK);
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

            Address address = _addressService
                    .saveAddresFromBloodDonorRegistration(_mapper.map(dto.getAddressBloodDonorDTO(), Address.class));
            BloodDonor bloodDonor = _bloodDonorService
                    .RegisterUser(_mapper.map(dto.getNonRegisteredBloodDonorInfoDTO(), BloodDonor.class), address);
            _accountService.registerRegisteredUser(_mapper.map(dto.getAccountDTO(), Account.class),
                    bloodDonor);

            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
