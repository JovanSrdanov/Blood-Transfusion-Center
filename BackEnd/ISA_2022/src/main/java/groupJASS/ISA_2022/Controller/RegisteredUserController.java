package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.RegisterdUserInfoDto;
import groupJASS.ISA_2022.DTO.RegisteredUserDTO;
import groupJASS.ISA_2022.Model.RegisteredUser;
import groupJASS.ISA_2022.Service.Implementations.RegisteredUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("registered-user")
public class RegisteredUserController {

    private final RegisteredUserService _registeredUserService;
    private final ModelMapper _modelMapper;

    @Autowired
    public RegisteredUserController(RegisteredUserService registeredUserService, ModelMapper modelMapper ) {
        this._registeredUserService = registeredUserService;
        this._modelMapper = modelMapper;
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

}
