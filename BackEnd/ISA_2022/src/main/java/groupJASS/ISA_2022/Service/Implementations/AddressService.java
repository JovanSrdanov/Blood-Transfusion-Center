package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Repository.AddressRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAddresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@Primary
public class AddressService implements IAddresService {

    private final AddressRepository _addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        _addressRepository = addressRepository;

    }

    @Override
    public List<Address> findAll() {
        return _addressRepository.findAll();
    }

    @Override
    public Address findById(UUID id) {

        if (_addressRepository.findById(id).isPresent()) {

            return _addressRepository.findById(id).get();
        }

        throw new NotFoundException("Address not found");
    }

    @Override
    public Address save(Address entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        return _addressRepository.save(entity);
    }


    @Override
    public void deleteById(UUID id) {
        _addressRepository.deleteById(id);

    }
}