package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Service.Implementations.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {

    private final AddressService _addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this._addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> findAll() {
        return new ResponseEntity<>(this._addressService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Address> save(@RequestBody Address entity) {
        return new ResponseEntity<>(this._addressService.save(entity), HttpStatus.CREATED);
    }

}
