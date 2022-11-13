package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Service.Interfaces.IAddresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {

    private final IAddresService _service;

    @Autowired
    public AddressController(IAddresService addressService) {
        this._service = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> findAll() {
        return new ResponseEntity<>((List<Address>) this._service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Address> save(@RequestBody Address entity) {
        return new ResponseEntity<>(this._service.save(entity), HttpStatus.CREATED);
    }

}
