package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Repository.AddressRepository;
import groupJASS.ISA_2022.Repository.BloodCenterRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
@Primary
public class BloodCenterService implements IBloodCenterService {

    private final BloodCenterRepository _bloodCenterRepository;
    private final AddressRepository _addressRepository;

    @Autowired
    public BloodCenterService(BloodCenterRepository bloodCenterRepository, AddressRepository addressRepository)
    {
        _bloodCenterRepository = bloodCenterRepository;
        _addressRepository = addressRepository;
    }
    @Override
    public Iterable<BloodCenter> findAll() {
        return _bloodCenterRepository.findAll();
    }

    @Override
    public BloodCenter findById(UUID id) throws NotFoundException {
        if (_bloodCenterRepository.findById(id).isPresent()) {
            return _bloodCenterRepository.findById(id).get();
        }
        throw new NotFoundException("Blood center not found");
    }

    @Override
    public BloodCenter save(BloodCenter entity) throws BadRequestException {
        if (entity.getId() == null) {
            entity.getAddress().setId(UUID.randomUUID());
            entity.setId(UUID.randomUUID());
        }

        if(!entity.getWorkingHours().isValid())
        {
            throw new BadRequestException("Invalid working hours");
        }

        Address address = entity.getAddress();
        //Don't look at this
        if(_addressRepository.existsAddressByStreetIgnoreCaseAndNumberIgnoreCaseAndCityIgnoreCaseAndCountryIgnoreCase(
                address.getStreet(), address.getNumber(), address.getCity(), address.getCountry()))
        {
            throw new DataIntegrityViolationException("Exact same address already exists");
        }

        return _bloodCenterRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _bloodCenterRepository.deleteById(id);
    }

    public Page<BloodCenter> findProductsWithSorting(int offset, int pageSize, String field, String sort, String s)
            throws SortNotFoundException {
        Page<BloodCenter> page;
        if(sort.isBlank()) {
            page = _bloodCenterRepository.searchBy(s, PageRequest.of(offset, pageSize));
        }
        else if(sort.equals("asc")) {
            page = _bloodCenterRepository.searchBy(s, PageRequest.of(offset, pageSize)
                    .withSort(Sort.by(Sort.Direction.ASC,field)));
        }
        else if(sort.equals("desc")) {
            page = _bloodCenterRepository.searchBy(s, PageRequest.of(offset, pageSize)
                    .withSort(Sort.by(Sort.Direction.DESC,field)));
        }
        else {
            throw new SortNotFoundException("This sort type doesn't exist");
        }
        return page;
    }
}
