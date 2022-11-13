package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Service.Interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {

    private final IAddressService _addressService;

    @Autowired
    public AddressController(IAddressService addressService) {
        this._addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> findAll() {
        return new ResponseEntity<>((List<Address>) this._addressService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Address> save(@RequestBody Address entity) {
        try {
            return new ResponseEntity<>(this._addressService.save(entity), HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
