package groupJASS.ISA_2022.Service.Implementations;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import groupJASS.ISA_2022.DTO.Appointment.AvailableCustomAppointmentsDto;
import groupJASS.ISA_2022.DTO.Appointment.AvailablePredefinedDto;
import groupJASS.ISA_2022.DTO.Appointment.PredefinedInCustomTimeDto;
import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterBasicInfoDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.*;
import groupJASS.ISA_2022.Repository.AppointmentRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodDonorService;
import groupJASS.ISA_2022.Utilities.ObjectMapperUtils;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

        throw new NotFoundException("Appointment not found");
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
    public List<DateRange> findFreeChunksForStaffId(UUID staffId, DateRange ceneterWorkRange) {
        // Nadji sve appointmente za nekog staff-a
        List<Appointment> appointments = _appointmentRepository.findTakenChunksByStaffId(staffId,
                ceneterWorkRange.getStartTime(), ceneterWorkRange.getEndTime());

        // Posto repo vraca listu appointmente moram da ih pretovrim u listu
        // dateRangeova
        List<DateRange> dateRanges = new ArrayList<>();
        for (Appointment a : appointments) {
            dateRanges.add(new DateRange(a.getTime().getStartTime(), a.getTime().getEndTime()));
        }

        // Od celog radnog vremena oduzmi zauzete chunkove i onda vracamo slobodne
        // (zelene)
        return DateRange.subtractFromBigRange(ceneterWorkRange, dateRanges);
    }

    @Override
    public List<DateRange> findFreeSlotsForStaffIds(List<UUID> staffIds, LocalDateTime date, int duration)
            throws BadRequestException {
        if (staffIds.size() == 0) {
            throw new BadRequestException("Nema staff ids");
        }

        try {
            Staff firstStaff = _staffService.findById(staffIds.get(0));
            UUID bcId = firstStaff.getBloodCenter().getId();

            for (UUID staffId : staffIds) {
                Staff staff = _staffService.findById(staffId);
                if (!staff.getBloodCenter().getId().equals(bcId)) {
                    throw new BadRequestException("Diffrent staff bloodCenter ids");
                }
            }
        } catch (Exception e) {
            throw new BadRequestException("Nema staff sa tim id");
        }

        // TODO proveri dateragne vece od 0

        // Nadji preseke izmedju slobodnih chunkova svih prosledjenih zaposlenih
        UUID bloodCenterId = _staffService.findById(staffIds.get(0)).getBloodCenter().getId();
        DateRange centerWorkRange = _bloodBloodCenterService.getWorkingDateRangeForDate(bloodCenterId, date);

        List<DateRange> intersections = new ArrayList<>();
        intersections.add(centerWorkRange);

        for (UUID staffId : staffIds) {
            intersections = DateRange.intersectTwoList(intersections,
                    findFreeChunksForStaffId(staffId, centerWorkRange));
        }

        // Podeli (iscepkaj) chunkove u manje slotove odredjene duzine
        List<DateRange> slots = new ArrayList<>();
        for (DateRange dr : intersections) {
            List<DateRange> listTwo = DateRange.splitBigRangeIntoSmallRanges(dr, duration);
            slots = Stream.concat(slots.stream(), listTwo.stream())
                    .collect(Collectors.toList());
        }

        return slots;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Appointment predefine(DateRange dateRange, List<UUID> staffIds, UUID staffAdminId, boolean isPredef)
            throws BadRequestException {
        // Provera da li je poslata prazna lista staff-ova
        if (staffIds.size() == 0) {
            throw new BadRequestException("There are no staffs");
        }

        // Provera da li su svi poslati staff iz istog bloodCentra
        UUID bcId = _staffService.findById(staffAdminId).getBloodCenter().getId();
        for (UUID staffId : staffIds) {
            Staff staff = _staffService.findById(staffId);
            if (!staff.getBloodCenter().getId().equals(bcId)) {
                throw new BadRequestException("Diffrent staff bloodCenter ids");
            }
        }

        // Provera da li je appointment koji pokusavamo da predefinisemo dostpuan
        List<DateRange> freeSlots = findFreeSlotsForStaffIds(staffIds, dateRange.getStartTime(),
                dateRange.calcaulateDurationMinutes());
        boolean found = false;
        for (DateRange r : freeSlots) {
            if (r.isEqual(dateRange)) {
                found = isPredef;
                break;
            }
        }
        if (!found) {
            throw new BadRequestException("No this time slot is not free");
        }

        // Predefinis appointment, kreiraj Appointment
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
    public Page<Appointment> getPremadeAppointmentsForBloodCenter(UUID centerId, UUID donorId, int page, int pageSize,
                                                                  String sort)
            throws SortNotFoundException {
        Page<Appointment> currentPage;
        if (sort.isBlank()) {
            currentPage = _appointmentRepository.searchBy(centerId, donorId, PageRequest.of(page, pageSize));
        } else if (sort.equals("asc")) {
            currentPage = _appointmentRepository.searchBy(centerId, donorId,
                    PageRequest.of(page, pageSize).withSort(Sort.by(Sort.Direction.ASC, "start_time")));
        } else if (sort.equals("desc")) {
            currentPage = _appointmentRepository.searchBy(centerId, donorId,
                    PageRequest.of(page, pageSize).withSort(Sort.by(Sort.Direction.DESC, "start_time")));
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
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class)
    public AppointmentSchedulingHistory scheduleAppointment(UUID donorId, UUID appointmentId) {
        if (_appointmentRepository.findById(appointmentId).isEmpty()) {
            throw new NotFoundException("Donor or appointment doesent exist");
        }

        // Provera da li je appointment koji pokusavamo da zakazemo idalje dostpuan
        Appointment appointment = findById(appointmentId);
        boolean found = false;
        var apps = _appointmentRepository.findAvailableAppointmentsForDonorIncludingCustom(donorId,
                appointment.getBloodCenter().getId());
        for (Appointment a : apps) {
            if (a.getId().equals(appointmentId)) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new NotFoundException("Appointment not available anymore");
        }

        // Zakazi appointment, kreiraj history
        try {
            BloodDonor donor = _bloodDonorService.findById(donorId);
            checkIfDonorCanSchedule(donor);

            var ash = _appointmentSchedulingHistoryService.save(new AppointmentSchedulingHistory(
                    null,
                    "QR",
                    LocalDateTime.now(),
                    AppointmentSchedulingConfirmationStatus.PENDING,
                    appointment,
                    donor,
                    null));
            String email = _accountService.findAccountByPersonId(donorId).getEmail();
            return ash;
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkIfDonorCanSchedule(BloodDonor donor) throws Exception {

        if (donor.getQuestionnaire() == null) {
            throw new Exception("Donor can not donate blood becouse he has not filled his questionnaire");
        }
        if (!donor.getQuestionnaire().canDonateBlood()) {
            throw new Exception("Donor can not donate blood becouse of his questionnaire");
        }
        if (donor.getPenalties() >= 3) {
            throw new Exception("Can not schedule because blood donor has over 3 penalties");
        }
        for (var ash : donor.getAppointmentSchedulingHistories()) {
            if (ash.getStatus() == AppointmentSchedulingConfirmationStatus.PROCESSED
                    && ash.getAppointment().getTime().getStartTime().isAfter(LocalDateTime.now().minusMonths(6))) {
                throw new Exception("You have donated blood recently");
            }
        }
    }

    @Override
    //Drugacije ne moze
    //Kada se zakazuje bilo koji od pregleda kreira se novi scheduling appointment history.
    //Da bi se pregled zakazao moraju se proci provere koje nadmasuju ogranicenja baze podataka.
    //Ni na koji drugi nacin ne moze da se zabrani insert novog reda u tabelu, sem da se podgine nivo izolacije na najvisi nivo
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public AppointmentSchedulingHistory scheduleCustomAppointment(UUID donorId, LocalDateTime time, UUID staffId)
            throws BadRequestException {
        DateRange dateRange = new DateRange(time, 20);
        // Proveri da li moze ovaj app da se zakaze tj el postoji slobodan slot u ovo vreme
        var availableAppointments = findCustomAvailableAppointments(donorId, time);
        boolean found = false;
        for (AvailableCustomAppointmentsDto apps : availableAppointments) {
            if (apps.getTime().isEqual(dateRange) && apps.getStaffId().equals(staffId)) {
                found = true;
                break;
            }
        }

        if (!found) {
            //Ne postoji slobodan slot u ovo vreme
            throw new BadRequestException("Can't schedule appointment for this time, staff and patient");
        }

        // Predefinis appointment, kreiraj Appointment
        HashSet<Staff> staffHashSet = new HashSet<>();
        staffHashSet.add(_staffService.findById(staffId));
        BloodCenter bloodCenter = staffHashSet.stream().toList().get(0).getBloodCenter();

        UUID appId = UUID.randomUUID();

        save(new Appointment(
                appId,
                staffHashSet,
                bloodCenter,
                false,
                dateRange));

        return scheduleAppointment(donorId, appId);
    }

    @Override
    public List<AvailableCustomAppointmentsDto> findCustomAvailableAppointments(UUID donorId, LocalDateTime time) {
        DateRange wantedRange = new DateRange(time, 20);
        List<AvailableCustomAppointmentsDto> availableCustomAppointments = new ArrayList<>();

        for (BloodCenter bc : _bloodBloodCenterService.findAll()) {
            DateRange centerWorkRange = _bloodBloodCenterService.getWorkingDateRangeForDate(bc.getId(), time);
            boolean foundFreeStaff = false;
            UUID foundStaffId = null;
            var staffs = new ArrayList<>(bc.getStaff().stream().toList());
            staffs.sort(new Comparator<Staff>() {
                @Override
                public int compare(Staff s1, Staff s2) {
                    return s1.getName().compareTo(s2.getName());
                }
            });

            for (Staff staff : staffs) {
                for (DateRange dr : findFreeChunksForStaffId(staff.getId(), centerWorkRange)) {
                    if (wantedRange.isSubrangeOf(dr)) {
                        foundFreeStaff = true;
                        foundStaffId = staff.getId();
                        break;
                    }
                }
                if (foundFreeStaff && foundStaffId != null) {
                    availableCustomAppointments.add(new AvailableCustomAppointmentsDto(
                            wantedRange,
                            ObjectMapperUtils.map(bc, BloodCenterBasicInfoDto.class),
                            foundStaffId));
                    break;
                }
            }
        }
        return availableCustomAppointments;
    }

    @Async
    public void sendScheduleConfirmation(Appointment appointment, String email, UUID ASHId) {

        try {
            System.out.println("Email sending started.");
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("psw.integrations.g4@gmail.com");
            helper.setTo(email);
            helper.setSubject("New appoitnment!");
            String confirmationInfo = "Hello!\n" +
                    "A new appointment has been scheduled!\n" +
                    "BloodCenter: " + appointment.getBloodCenter().getName() + "\n" +
                    "Date and time: "
                    + appointment.getTime().getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    + "\n" +
                    "Duration: " + appointment.getTime().calcaulateDurationMinutes() + "min\n";
            helper.setText(confirmationInfo);

            System.out.println("QR CODE GENERATING STARTED");

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            String qrCodeText = "Your appointment is scheduled at "
                    + appointment.getTime().getStartTime().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy.")) +
                    " in blood center " + appointment.getBloodCenter().getName() + ".\n=====\n" +
                    "Appointment code: " + ASHId;
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 300, 300);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] qrCode = pngOutputStream.toByteArray();

            System.out.println("QR CODE GENERATING FINISHED");

            helper.addAttachment("qrCode.png", new ByteArrayResource(qrCode), "image/png");
            javaMailSender.send(mimeMessage);

            System.out.println("Email sending complete.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PredefinedInCustomTimeDto> findAllByTimeStartTime(LocalDateTime startTime) {
        List<Appointment> lista = _appointmentRepository.findAllByTimeStartTime(startTime);
        return ObjectMapperUtils.mapAll(_appointmentRepository.findAllByTimeStartTime(startTime), PredefinedInCustomTimeDto.class);
    }

}
