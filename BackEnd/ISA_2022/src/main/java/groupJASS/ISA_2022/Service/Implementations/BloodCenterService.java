package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Model.BloodGroup;
import groupJASS.ISA_2022.Model.BloodQuantity;
import groupJASS.ISA_2022.Repository.AddressRepository;
import groupJASS.ISA_2022.Repository.BloodCenterRepository;
import groupJASS.ISA_2022.Repository.BloodQuantityRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.*;

@Service
@Primary
public class BloodCenterService implements IBloodCenterService {

    private final BloodCenterRepository _bloodCenterRepository;
    private final AddressRepository _addressRepository;
    private final BloodQuantityRepository _bloodQuantityRepository;

    @Autowired
    public BloodCenterService(BloodCenterRepository bloodCenterRepository, AddressRepository addressRepository, BloodQuantityRepository bloodQuantityRepository)
    {
        _bloodCenterRepository = bloodCenterRepository;
        _addressRepository = addressRepository;
        _bloodQuantityRepository = bloodQuantityRepository;
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

    private Set<BloodQuantity> initiateBloodQuantities()
    {
        Set<BloodQuantity> bloodQuantities = new HashSet<>();
        for(BloodGroup bloodGroup : BloodGroup.values())
        {
           bloodQuantities.add(new BloodQuantity(bloodGroup));
        }
        return  bloodQuantities;
    }

    @Override
    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public BloodCenter save(BloodCenter entity) throws BadRequestException {
        if (entity.getId() == null) {
            Address address = entity.getAddress();
            //Don't look at this
            if(_addressRepository.existsAddressByStreetIgnoreCaseAndNumberIgnoreCaseAndCityIgnoreCaseAndCountryIgnoreCase(
                    address.getStreet(), address.getNumber(), address.getCity(), address.getCountry()))
            {
                throw new DataIntegrityViolationException("Exact same address already exists");
            }
            address.setId(UUID.randomUUID());

            entity.setBloodQuantities(initiateBloodQuantities());
            entity.setId(UUID.randomUUID());
        }

        if(!entity.getWorkingHours().isValid())
        {
            throw new BadRequestException("Invalid working hours");
        }


        return _bloodCenterRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _bloodCenterRepository.deleteById(id);
    }

    public Page<BloodCenter> findProductsWithSorting(int offset, int pageSize, String field, String s){
        return  _bloodCenterRepository.searchBy(s, PageRequest.of(offset, pageSize)
                .withSort(Sort.by(Sort.Direction.ASC,field)));
    }
}
