package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.DTO.Appointment.AvailablePredefinedDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.*;
import groupJASS.ISA_2022.Repository.AppointmentRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodDonorService;
import groupJASS.ISA_2022.Utilities.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private final IBloodDonorService _bloodDonorService;
    private final AppointmentSchedulingHistoryService _appointmentSchedulingHistoryService;
    private final IBloodCenterService _bloodBloodCenterService;
    private final IAccountService _accountService;
    private final StaffService _staffService;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,
                              IBloodDonorService bloodDonorService,
                              AppointmentSchedulingHistoryService appointmentSchedulingHistoryService,
                              StaffService staffService,
                              IBloodCenterService bloodBloodCenterService,
                              IAccountService accountService) {
        this._appointmentRepository = appointmentRepository;
        this._bloodDonorService = bloodDonorService;
        this._appointmentSchedulingHistoryService = appointmentSchedulingHistoryService;
        this._staffService = staffService;
        this._bloodBloodCenterService = bloodBloodCenterService;
        this._accountService = accountService;

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
        // Nadji sve app za nekog staff-a
        List<Appointment> appointments = _appointmentRepository.findTakenChunksByStaffId(staffId,
                bigRange.getStartTime(), bigRange.getEndTime());

        // Posto repo vraca listu appointmente moram da ih pretovrim u listu
        // dateRangeova
        List<DateRange> dateRanges = new ArrayList<>();
        for (Appointment a : appointments) {
            dateRanges.add(new DateRange(a.getTime().getStartTime(), a.getTime().getEndTime()));
        }

        // Uzmi slobodne chunkove (zelene)
        return DateRange.subtractFromBigRange(bigRange, dateRanges);
    }

    @Override
    public List<DateRange> findFreeSlotsForStaffIds(List<UUID> staffIds, LocalDateTime date, int duration)
            throws BadRequestException {
        UUID bcId = _staffService.findById(staffIds.get(0)).getBloodCenter().getId();
        for (UUID staffId : staffIds) {
            Staff staff = _staffService.findById(staffId);
            if (!staff.getBloodCenter().getId().equals(bcId)) {
                throw new BadRequestException("Diffrent staff bloodCenter ids");
            }
        }

        UUID bloodCenterId = _staffService.findById(staffIds.get(0)).getBloodCenter().getId();
        DateRange bigRange = _bloodBloodCenterService.getWorkingDateRangeForDate(bloodCenterId, date);

        // Todo proveri da li su svi id staffa iz istog blood centra
        // Todo proveri da li su svi postoje staff ids, ne sme prazno
        // TODO proveri dateragne vece od 0
        // TODO provera za radno vreme bolnice

        List<DateRange> intersections = new ArrayList<>();
        intersections.add(bigRange);

        for (UUID staffId : staffIds) {
            intersections = DateRange.intersectTwoList(intersections,
                    findFreeSlotsForStaffId(staffId, bigRange, duration));
        }

        // Podeli chunkove u manje slotove odredjene duzine
        List<DateRange> slots = new ArrayList<>();
        for (DateRange dr : intersections) {
            List<DateRange> listTwo = DateRange.splitBigRangeIntoSmallRanges(dr, duration);
            slots = Stream.concat(slots.stream(), listTwo.stream())
                    .collect(Collectors.toList());
        }

        return slots;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Appointment predefine(DateRange dateRange, List<UUID> staffIds, UUID staffAdminId)
            throws BadRequestException {
        UUID bcId = _staffService.findById(staffAdminId).getBloodCenter().getId();
        for (UUID staffId : staffIds) {
            Staff staff = _staffService.findById(staffId);
            if (!staff.getBloodCenter().getId().equals(bcId)) {
                throw new BadRequestException("Diffrent staff bloodCenter ids");
            }
        }

        List<DateRange> freeSlots = findFreeSlotsForStaffIds(staffIds, dateRange.getStartTime(),
                dateRange.calcaulateDurationMinutes());
        boolean found = false;
        for (DateRange r : freeSlots) {
            if (r.isEqual(dateRange)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new BadRequestException("Ne ovaj termin nije slobodan");
        }

        HashSet<Staff> staffHashSet = new HashSet<>();
        for (UUID id : staffIds) {
            staffHashSet.add(_staffService.findById(id));
        }
        BloodCenter bloodCenter = staffHashSet.stream().toList().get(0).getBloodCenter();

        return save(new Appointment(
                null,
                staffHashSet,
                bloodCenter,
                true,
                dateRange));
    }

    @Override
    public Page<Appointment> getPremadeAppointmentsForBloodCenter(UUID centerId, UUID donorId, int page, int pageSize, String sort)
            throws SortNotFoundException {
        Page<Appointment> currentPage;
        if (sort.isBlank()) {
            currentPage = _appointmentRepository.searchBy(centerId, donorId, PageRequest.of(page, pageSize));
        } else if (sort.equals("asc")) {
            currentPage = _appointmentRepository.searchBy(centerId, donorId, PageRequest.of(page, pageSize));
        } else if (sort.equals("desc")) {
            currentPage = _appointmentRepository.searchBy(centerId, donorId, PageRequest.of(page, pageSize));
        } else {
            throw new SortNotFoundException("This sort type doesn't exist");
        }
        return currentPage;
    }

    @Override
    public List<AvailablePredefinedDto> findAvailableAppointmentsForDonor(UUID donorId, UUID centerId) {
        return ObjectMapperUtils.mapAll(_appointmentRepository.findAvailableAppointmentsForDonor(donorId, centerId),
                AvailablePredefinedDto.class);
    }

    @Override
    @Async
    public AppointmentSchedulingHistory scheduleAppointment(UUID donorId, UUID appointmentId) {
        if (_appointmentRepository.findById(appointmentId).isEmpty()) {
            throw new NotFoundException("Donor or appointment doesent exist");
        }

        Appointment appointment = findById(appointmentId);
        boolean found = false;
        for (Appointment a : _appointmentRepository.findAvailableAppointmentsForDonor(donorId,
                appointment.getBloodCenter().getId())) {
            if (a.getId().equals(appointmentId)) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new NotFoundException("Appointment not available anymore");
        }

        try {
            BloodDonor donor = _bloodDonorService.findById(donorId);

            var ash = _appointmentSchedulingHistoryService.save(new AppointmentSchedulingHistory(
                    null,
                    "QR",
                    LocalDateTime.now(),
                    AppointmentSchedulingConfirmationStatus.PENDING,
                    appointment,
                    donor,
                    null));
            String email = _accountService.findAccountByPersonId(donorId).getEmail();
            sendScheduleConfirmation(appointment, email);
            return ash;
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

    }

    @Async
    public void sendScheduleConfirmation(Appointment appointment, String email) {

        System.out.println("Email se salje sa informacijama o pregledu!");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom("ISA_BEJBI");
        mail.setSubject("New appointment! ");

        String confirmationInfo = "Hello!\n" +
                "A new appointment has been scheduled!\n" +
                "BloodCenter: " + appointment.getBloodCenter().getName() + "\n" +
                "Date and time: " + appointment.getTime().getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n" +
                "Duration: " + appointment.getTime().calcaulateDurationMinutes() + "min\n";

        mail.setText(confirmationInfo);
        javaMailSender.send(mail);
        System.out.println("Email poslat!");
    }

}
