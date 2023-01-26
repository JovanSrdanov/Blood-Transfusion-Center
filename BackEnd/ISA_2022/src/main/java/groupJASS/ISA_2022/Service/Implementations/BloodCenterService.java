package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterIncomingAppointmentDto;
import groupJASS.ISA_2022.DTO.BloodCenter.WorkingHoursRoundedDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Exceptions.BloodCenterNotAssignedException;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.*;
import groupJASS.ISA_2022.Repository.*;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Primary
public class BloodCenterService implements IBloodCenterService {

    private final BloodCenterRepository _bloodCenterRepository;
    private final AddressRepository _addressRepository;
    private final BloodQuantityRepository _bloodQuantityRepository;
    private final AccountRepository _accountRepository;
    private final StaffRepository _staffRepository;
    private final ModelMapper _mapper;

    @Autowired
    public BloodCenterService(BloodCenterRepository bloodCenterRepository, AddressRepository addressRepository,
                              BloodQuantityRepository bloodQuantityRepository, AccountRepository accountRepository,
                              StaffRepository staffRepository, ModelMapper modelMapper) {
        _bloodCenterRepository = bloodCenterRepository;
        _addressRepository = addressRepository;
        _bloodQuantityRepository = bloodQuantityRepository;
        _accountRepository = accountRepository;
        _staffRepository = staffRepository;
        _mapper = modelMapper;
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
    public WorkingHoursRoundedDto getRoundedWorkingHours(Principal principal) throws BloodCenterNotAssignedException {
        Account account = _accountRepository.findByEmail(principal.getName());
        Staff staff = _staffRepository.findById(account.getPersonId()).get();

        if (staff.getBloodCenter() == null) {
            throw new BloodCenterNotAssignedException("There is no blood center assigned for given staff");
        }

        WorkingHours workingHours = staff.getBloodCenter().getWorkingHours();
        int workingHoursStart = workingHours.getStartHours();
        int workingHoursEnd = workingHours.getEndMinutes() > 0 ? workingHours.getEndHours() + 1 : workingHours.getEndHours();

        return new WorkingHoursRoundedDto(workingHoursStart, workingHoursEnd);
    }

    private Set<BloodQuantity> initiateBloodQuantities() {
        Set<BloodQuantity> bloodQuantities = new HashSet<>();
        for (BloodGroup bloodGroup : BloodGroup.values()) {
            bloodQuantities.add(new BloodQuantity(bloodGroup));
        }
        return bloodQuantities;
    }

    @Override
    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public BloodCenter save(BloodCenter entity) throws BadRequestException {
        if (entity.getId() == null) {
            Address address = entity.getAddress();
            //Don't look at this
            if (_addressRepository.existsAddressByStreetIgnoreCaseAndNumberIgnoreCaseAndCityIgnoreCaseAndCountryIgnoreCase(
                    address.getStreet(), address.getNumber(), address.getCity(), address.getCountry())) {
                throw new DataIntegrityViolationException("Exact same address already exists");
            }
            address.setId(UUID.randomUUID());

            entity.setBloodQuantities(initiateBloodQuantities());
            entity.setId(UUID.randomUUID());
        }

        if (!entity.getWorkingHours().isValid()) {
            throw new BadRequestException("Invalid working hours");
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
        if (sort.isBlank()) {
            page = _bloodCenterRepository.searchBy(s, PageRequest.of(offset, pageSize));
        } else if (sort.equals("asc")) {
            page = _bloodCenterRepository.searchBy(s, PageRequest.of(offset, pageSize)
                    .withSort(Sort.by(Sort.Direction.ASC, field)));
        } else if (sort.equals("desc")) {
            page = _bloodCenterRepository.searchBy(s, PageRequest.of(offset, pageSize)
                    .withSort(Sort.by(Sort.Direction.DESC, field)));
        } else {
            throw new SortNotFoundException("This sort type doesn't exist");
        }
        return page;
    }

    @Override
    public DateRange getWorkingDateRangeForDate(UUID id, LocalDateTime date) {
        BloodCenter bloodCenter = findById(id);
        WorkingHours wa = bloodCenter.getWorkingHours();

        LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(),
                date.getDayOfMonth(), wa.getStartHours(), wa.getStartMinutes(), 0);

        LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(),
                date.getDayOfMonth(), wa.getEndHours(), wa.getEndMinutes(), 0);

        return new DateRange(start, end);
    }

    @Override
    public List<BloodCenterIncomingAppointmentDto> getIncomingAppointments(Principal principal) throws BloodCenterNotAssignedException {
        Account account = _accountRepository.findByEmail(principal.getName());
        Staff staff = _staffRepository.findById(account.getPersonId()).get();

        if (staff.getBloodCenter() == null) {
            throw new BloodCenterNotAssignedException("There is no blood center assigned for given staff");
        }
        return _bloodCenterRepository.getIncomingAppointments(staff.getBloodCenter().getId(), LocalDateTime.now());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callTheHelicopter(BloodCenter bloodCenter) throws Exception {
        if (_bloodCenterRepository.existsBloodCenterByDeliveryInProgres()) {
            throw new Exception("Helicpoter is currently delivering blood");
        }
        if (bloodCenter.isHelicopterHere()) {

            throw new Exception("Helicpoter is already here");
        }
        _bloodCenterRepository.removeHelicopterFromOtherHospital();
        bloodCenter.setHelicopterHere(true);
        save(bloodCenter);

    }

    @Override
    public void DonateBloodToHospital(BloodCenter bloodCenter, GPSDemandBloodShipment shipment) {
        for (var BQ : bloodCenter.getBloodQuantities()) {
            System.out.println(BQ.getBloodGroup() + " " + BQ.getQuantity());

            switch (BQ.getBloodGroup()) {
                case A_POS -> {
                    BQ.setQuantity(Math.max(BQ.getQuantity() - Math.max(shipment.getA_pos(), 0), 0));
                }
                case A_NEG -> {
                    BQ.setQuantity(Math.max(BQ.getQuantity() - Math.max(shipment.getA_neg(), 0), 0));
                }
                case B_POS -> {
                    BQ.setQuantity(Math.max(BQ.getQuantity() - Math.max(shipment.getB_pos(), 0), 0));
                }
                case B_NEG -> {
                    BQ.setQuantity(Math.max(BQ.getQuantity() - Math.max(shipment.getB_neg(), 0), 0));
                }
                case O_POS -> {
                    BQ.setQuantity(Math.max(BQ.getQuantity() - Math.max(shipment.getO_pos(), 0), 0));
                }
                case O_NEG -> {
                    BQ.setQuantity(Math.max(BQ.getQuantity() - Math.max(shipment.getO_neg(), 0), 0));
                }
                case AB_POS -> {
                    BQ.setQuantity(Math.max(BQ.getQuantity() - Math.max(shipment.getAb_pos(), 0), 0));
                }
                case AB_NEG -> {
                    BQ.setQuantity(Math.max(BQ.getQuantity() - Math.max(shipment.getAb_neg(), 0), 0));
                }
            }
            System.out.println(BQ.getBloodGroup() + " " + BQ.getQuantity());
            _bloodQuantityRepository.save(BQ);
        }

    }
}
