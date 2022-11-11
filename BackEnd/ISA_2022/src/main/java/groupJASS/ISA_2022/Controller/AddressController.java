package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Service.Implementations.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Address> findAll() {
        return this._addressService.findAll();
    }

    @PostMapping
    public Address save(@RequestBody Address entity) {
        return this._addressService.save(entity);
    }

}
