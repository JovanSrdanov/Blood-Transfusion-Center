package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.RegisterNonRegisteredUserDTO;
import groupJASS.ISA_2022.DTO.RegisterdUserInfoDto;
import groupJASS.ISA_2022.DTO.RegisteredUserDTO;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodUser;
import groupJASS.ISA_2022.Model.RegisteredUser;
import groupJASS.ISA_2022.Service.Implementations.AddressService;
import groupJASS.ISA_2022.Service.Implementations.BloodUserService;
import groupJASS.ISA_2022.Service.Implementations.RegisteredUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("registered-user")
public class RegisteredUserController {

    private final RegisteredUserService _registeredUserService;
    private final AddressService _addressService;
    private final BloodUserService _bloodUserService;
    private final ModelMapper _modelMapper;

    @Autowired
    public RegisteredUserController(RegisteredUserService registeredUserService, ModelMapper modelMapper, AddressService addressService, BloodUserService bloodUserService) {
        this._registeredUserService = registeredUserService;
        this._modelMapper = modelMapper;
        this._addressService = addressService;
        this._bloodUserService = bloodUserService;
    }

    @GetMapping
    public ResponseEntity<List<RegisteredUser>> findAll() {
        var res = this._registeredUserService.findAll();
        var first = res.get(0);
        RegisteredUserDTO regUsDto = new RegisteredUserDTO();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<RegisterdUserInfoDto> findById(@PathVariable UUID id) {
        RegisteredUser user = this._registeredUserService.findById(id);
        return new ResponseEntity<>(new RegisterdUserInfoDto(user), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<RegisteredUser> save(@RequestBody RegisteredUser entity) {
        return new ResponseEntity<>(this._registeredUserService.save(entity), HttpStatus.CREATED);
    }

    @PostMapping("register")
    public ResponseEntity<?> Register(@Valid @RequestBody RegisterNonRegisteredUserDTO dto) throws ConstraintViolationException {

        try {
           
            Address address = _addressService.AddresFromUserRegistration(_modelMapper.map(dto.getAddressRegUserDTO(), Address.class));
            RegisteredUser registeredUser = _registeredUserService.RegisterUser(_modelMapper.map(dto.getNonRegisteredUserInfoDTO(), RegisteredUser.class), address);
            _bloodUserService.registerRegisteredUser(_modelMapper.map(dto.getBloodUserDTO(), BloodUser.class), registeredUser);

            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
