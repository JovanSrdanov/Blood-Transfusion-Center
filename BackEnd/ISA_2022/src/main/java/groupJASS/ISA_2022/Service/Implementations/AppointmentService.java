package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.Appointment;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Model.DateRange;
import groupJASS.ISA_2022.Model.Staff;
import groupJASS.ISA_2022.Repository.AppointmentRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Primary
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepository _appointmentRepository;
    private final IBloodCenterService _bloodBloodCenterService;
    private final StaffService _staffService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, StaffService staffService,
                              IBloodCenterService bloodBloodCenterService) {
        this._appointmentRepository = appointmentRepository;
        this._staffService = staffService;
        this._bloodBloodCenterService = bloodBloodCenterService;
    }

    @Override
    public Iterable<Appointment> findAll() {
        return _appointmentRepository.findAll();
    }

    @Override
    public Appointment findById(UUID id) {
        if (_appointmentRepository.findById(id).isPresent()) {

            return _appointmentRepository.findById(id).get();
        }

        throw new NotFoundException("User not found");
    }

    @Override
    public Appointment save(Appointment entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        return _appointmentRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> findAllThatOverlap() {
        return _appointmentRepository.findAllThatOverlap();
    }

    @Override
    public List<DateRange> findFreeSlotsForStaffId(UUID staffId, DateRange bigRange, int duration) {
        //Nadji sve app za nekog staff-a
        List<Appointment> appointments =
                _appointmentRepository.findTakenChunksByStaffId(staffId, bigRange.getStartTime(), bigRange.getEndTime());

        //Posto repo vraca listu appointmente moram da ih pretovrim u listu dateRangeova
        List<DateRange> dateRanges = new ArrayList<>();
        for(Appointment a : appointments) {
            dateRanges.add(new DateRange(a.getTime().getStartTime(), a.getTime().getEndTime()));
        }

        //Uzmi slobodne chunkove (zelene)
        return DateRange.subtractFromBigRange(bigRange, dateRanges);
    }

    @Override
    public List<DateRange> findFreeSlotsForStaffIds(List<String> staffIds, LocalDateTime date, int duration) {
        UUID bloodCenterId = _staffService.findById(UUID.fromString(staffIds.get(0))).getBloodCenter().getId();
        DateRange bigRange = _bloodBloodCenterService.getWorkingDateRangeForDate(bloodCenterId, date);

        // Todo proveri da li su svi id staffa iz istog blood centra
        // Todo proveri da li su svi postoje staff ids, ne sme prazno
        // TODO proveri dateragne vece od 0
        // TODO provera za radno vreme bolnice

        List<DateRange> intersections = new ArrayList<>();
        intersections.add(bigRange);

        for (String staffId : staffIds) {
            intersections = DateRange.intersectTwoList(intersections,
                    findFreeSlotsForStaffId(UUID.fromString(staffId), bigRange, duration));
        }

        //Podeli chunkove u manje slotove odredjene duzine
        List<DateRange> slots = new ArrayList<>();
        for(DateRange dr : intersections) {
            List<DateRange> listTwo = DateRange.splitBigRangeIntoSmallRanges(dr, duration);
            slots = Stream.concat(slots.stream(), listTwo.stream())
                    .collect(Collectors.toList());
        }

        return slots;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Appointment predefine(DateRange dateRange, List<UUID> staffIds) {
        HashSet<Staff> staffHashSet = new HashSet<>();

        // TODO: beskonacno mozes puta zakazati isti termin
        for(UUID id : staffIds) {
            staffHashSet.add(_staffService.findById(id));
        }

        //TODO: ako bloodCentar ne pripada adminu koji ga zakazuje onda ne moze da zakaze
        //takodje i staff mora pripadati tom centru, ne moze da zakaze nekom drugom staff-u
        BloodCenter bloodCenter = staffHashSet.stream().toList().get(0).getBloodCenter();

        return save(new Appointment(
                null,
                staffHashSet,
                bloodCenter,
                true,
                dateRange
        ));
    }
}
