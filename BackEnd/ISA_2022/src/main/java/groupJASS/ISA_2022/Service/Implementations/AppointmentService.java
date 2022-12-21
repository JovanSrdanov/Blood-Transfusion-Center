package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.Appointment;
import groupJASS.ISA_2022.Model.DateRange;
import groupJASS.ISA_2022.Repository.AppointmentRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Primary
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepository _appointmentRepository;
    private final StaffService _staffService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, StaffService staffService) {
        this._appointmentRepository = appointmentRepository;
        this._staffService = staffService;
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
    public List<DateRange> findDefinedByStaffId(String staffId) {
        //Nadji sve app za nekog staff-a
        List<Appointment> appointments = _appointmentRepository.findDefinedByStaffId(UUID.fromString(staffId));

        //Posto repo vraca listu appointmente moram da ih pretovrim u listu dateRangeova
        List<DateRange> dateRanges = new ArrayList<>();
        for(Appointment a : appointments) {
            dateRanges.add(new DateRange(a.getTime().getStartTime(), a.getTime().getEndTime()));
        }

        //Ovo je radno vreme centra
        LocalDateTime start = LocalDateTime.of(2022, Month.DECEMBER, 21, 5, 0, 0);
        LocalDateTime end = LocalDateTime.of(2022, Month.DECEMBER, 21, 11, 30, 0);
        DateRange bigRange = new DateRange(start, end);

        //Uzmi slobodne chunkove (zelene)
        List<DateRange> rangesToSplit = DateRange.subtractFromBigRange(bigRange, dateRanges);

        //Podeli chunkove u manje slotove odredjene duzine
        List<DateRange> slots = new ArrayList<>();
        for(DateRange dr : rangesToSplit) {
            List<DateRange> listTwo = DateRange.splitBigRangeIntoSmallRanges(dr, 13);
            slots = Stream.concat(slots.stream(), listTwo.stream())
                    .collect(Collectors.toList());
        }

        return slots;
    }


}
