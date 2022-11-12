package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.Model.RegisteredUser;
import groupJASS.ISA_2022.Service.Implementations.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("registered-user")
public class RegisteredUserController {

    private final RegisteredUserService _registeredUserService;

    @Autowired
    public RegisteredUserController(RegisteredUserService _registeredUserService) {
        this._registeredUserService = _registeredUserService;
    }

    @GetMapping
    public ResponseEntity<List<RegisteredUser>> findAll() {
        return new ResponseEntity<>(this._registeredUserService.findAll(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<RegisteredUser> save(@RequestBody RegisteredUser entity) {
        return new ResponseEntity<>(this._registeredUserService.save(entity), HttpStatus.CREATED);
    }

}
