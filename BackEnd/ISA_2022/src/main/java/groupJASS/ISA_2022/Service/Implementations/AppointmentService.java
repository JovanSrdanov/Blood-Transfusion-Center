package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.DTO.Appointment.AvailableCustomAppointmentsDto;
import groupJASS.ISA_2022.DTO.Appointment.AvailablePredefinedDto;
import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterBasicInfoDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.*;
import groupJASS.ISA_2022.Repository.AppointmentRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodDonorService;
import groupJASS.ISA_2022.Utilities.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Primary
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepository _appointmentRepository;

    private  final IBloodDonorService _bloodDonorService;
    private final AppointmentSchedulingHistoryService _appointmentSchedulingHistoryService;
    private final IBloodCenterService _bloodBloodCenterService;
    private final StaffService _staffService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,
                              IBloodDonorService bloodDonorService,
                              AppointmentSchedulingHistoryService appointmentSchedulingHistoryService,
                              StaffService staffService,
                              IBloodCenterService bloodBloodCenterService) {
        this._appointmentRepository = appointmentRepository;
        this._bloodDonorService = bloodDonorService;
        this._appointmentSchedulingHistoryService = appointmentSchedulingHistoryService;
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
    public List<DateRange> findFreeChunksForStaffId(UUID staffId, DateRange ceneterWorkRange) {
        //Nadji sve appointmente za nekog staff-a
        List<Appointment> appointments =
                _appointmentRepository.findTakenChunksByStaffId(staffId, ceneterWorkRange.getStartTime(), ceneterWorkRange.getEndTime());

        //Posto repo vraca listu appointmente moram da ih pretovrim u listu dateRangeova
        List<DateRange> dateRanges = new ArrayList<>();
        for (Appointment a : appointments) {
            dateRanges.add(new DateRange(a.getTime().getStartTime(), a.getTime().getEndTime()));
        }

        //Od celog radnog vremena oduzmi zauzete chunkove i onda vracamo slobodne (zelene)
        return DateRange.subtractFromBigRange(ceneterWorkRange, dateRanges);
    }

    @Override
    public List<DateRange> findFreeSlotsForStaffIds(List<UUID> staffIds, LocalDateTime date, int duration) throws BadRequestException {
        if(staffIds.size() == 0) {
            throw new BadRequestException("Nema staff ids");
        }

        UUID bcId = _staffService.findById(staffIds.get(0)).getBloodCenter().getId();
        for(UUID staffId : staffIds) {
            Staff staff = _staffService.findById(staffId);
            if(!staff.getBloodCenter().getId().equals(bcId)) {
                throw new BadRequestException("Diffrent staff bloodCenter ids");
            }
        }

        //TODO proveri dateragne vece od 0

        //Nadji preseke izmedju slobodnih chunkova svih prosledjenih zaposlenih
        UUID bloodCenterId = _staffService.findById(staffIds.get(0)).getBloodCenter().getId();
        DateRange centerWorkRange = _bloodBloodCenterService.getWorkingDateRangeForDate(bloodCenterId, date);

        List<DateRange> intersections = new ArrayList<>();
        intersections.add(centerWorkRange);

        for (UUID staffId : staffIds) {
            intersections = DateRange.intersectTwoList(intersections,
                    findFreeChunksForStaffId(staffId, centerWorkRange));
        }

        //Podeli (iscepkaj) chunkove u manje slotove odredjene duzine
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
    public Appointment predefine(DateRange dateRange, List<UUID> staffIds, UUID staffAdminId, boolean isPredef) throws BadRequestException {
        //Provera da li je poslata prazna lista staff-ova
        if(staffIds.size() == 0) {
            throw new BadRequestException("Nema staff ids");
        }

        //Provera da li su svi poslati staff iz istog bloodCentra
        UUID bcId = _staffService.findById(staffAdminId).getBloodCenter().getId();
        for(UUID staffId : staffIds) {
            Staff staff = _staffService.findById(staffId);
            if(!staff.getBloodCenter().getId().equals(bcId)) {
                throw new BadRequestException("Diffrent staff bloodCenter ids");
            }
        }

        //Provera da li je appointment koji pokusavamo da predefinisemo dostpuan
        List<DateRange> freeSlots = findFreeSlotsForStaffIds(staffIds, dateRange.getStartTime(), dateRange.calcaulateDurationMinutes());
        boolean found = false;
        for(DateRange r : freeSlots) {
            if(r.isEqual(dateRange)) {
                found = isPredef;
                break;
            }
        }
        if(!found) {
            throw new BadRequestException("Ne, ovaj termin nije slobodan");
        }

        //Predefinis appointment, kreiraj Appointment
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
                dateRange
        ));
    }

    @Override
    public List<AvailablePredefinedDto> findAvailableAppointmentsForDonor(UUID donorId, UUID centerId) {
        return ObjectMapperUtils.mapAll(_appointmentRepository.findAvailableAppointmentsForDonor(donorId, centerId), AvailablePredefinedDto.class);
    }

    @Override
    public AppointmentSchedulingHistory scheduleAppointment(UUID donorId, UUID appointmentId) {
        if(_appointmentRepository.findById(appointmentId).isEmpty()) {
            throw new NotFoundException("Donor or appointment doesent exist");
        }

        //Provera da li je appointment koji pokusavamo da zakazemo idalje dostpuan
        Appointment appointment = findById(appointmentId);
        boolean found = false;
        var apps = _appointmentRepository.findAvailableAppointmentsForDonorIncludingCustom(donorId, appointment.getBloodCenter().getId());
        for(Appointment a :apps) {
            if(a.getId().equals(appointmentId)) {
                found = true;
                break;
            }
        }

        if(!found) {
            throw new NotFoundException("Appointment not available anymore");
        }

        //Zakazi appointment, kreiraj history
        try {
            BloodDonor donor = _bloodDonorService.findById(donorId);

            return _appointmentSchedulingHistoryService.save(new AppointmentSchedulingHistory(
                    null,
                    "QR",
                    LocalDateTime.now(),
                    AppointmentSchedulingConfirmationStatus.PENDING,
                    appointment,
                    donor,
                    null
            ));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public AppointmentSchedulingHistory scheduleCustomAppointmetn(UUID donorId, LocalDateTime time, UUID staffId) throws BadRequestException {
        DateRange dateRange = new DateRange(time, 20);
        //Proveri da li moze ovaj app da se zakaze tj el postoji
        var availableAppointments = findCustomAvailableAppointments(donorId, time);
        boolean found = false;
        for(AvailableCustomAppointmentsDto apps : availableAppointments) {
            if(apps.getTime().isEqual(dateRange) && apps.getStaffId().equals(staffId)) {
                found = true;
                break;
            }
        }

        if(!found) {
            throw new BadRequestException("Ne postoji ovaj custom app");
        }

        //Predefinis appointment, kreiraj Appointment
        HashSet<Staff> staffHashSet = new HashSet<>();
        staffHashSet.add(_staffService.findById(staffId));
        BloodCenter bloodCenter = staffHashSet.stream().toList().get(0).getBloodCenter();

        UUID appId = UUID.randomUUID();

        save(new Appointment(
                appId,
                staffHashSet,
                bloodCenter,
                false,
                dateRange
        ));

        return scheduleAppointment(donorId, appId);
    }

    @Override
    public List<AvailableCustomAppointmentsDto> findCustomAvailableAppointments(UUID donorId, LocalDateTime time) {
        DateRange wantedRange = new DateRange(time, 20);
        List<AvailableCustomAppointmentsDto> availableCustomAppointments = new ArrayList<>();

        for(BloodCenter bc : _bloodBloodCenterService.findAll()) {
            DateRange centerWorkRange = _bloodBloodCenterService.getWorkingDateRangeForDate(bc.getId(), time);
            boolean foundFreeStaff = false;
            UUID foundStaffId = null;
            var staffs = new ArrayList<>(bc.getStaff().stream().toList());
            staffs.sort(new Comparator<Staff>() {
                @Override
                public int compare(Staff s1, Staff s2) {
                    return s1.getId().compareTo(s2.getId());
                }
            });

            for(Staff staff : staffs) {
                for(DateRange dr : findFreeChunksForStaffId(staff.getId(), centerWorkRange)) {
                    if(wantedRange.isSubrangeOf(dr)) {
                        foundFreeStaff = true;
                        foundStaffId = staff.getId();
                        break;
                    }
                }
                if(foundFreeStaff && foundStaffId != null) {
                    availableCustomAppointments.add(new AvailableCustomAppointmentsDto(
                            wantedRange,
                            ObjectMapperUtils.map(bc, BloodCenterBasicInfoDto.class),
                            foundStaffId
                    ));
                    break;
                }
            }
        }
        return availableCustomAppointments;
    }


}
