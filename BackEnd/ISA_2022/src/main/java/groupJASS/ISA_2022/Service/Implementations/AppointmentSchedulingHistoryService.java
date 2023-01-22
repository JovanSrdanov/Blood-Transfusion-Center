package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.*;
import groupJASS.ISA_2022.Repository.AccountRepository;
import groupJASS.ISA_2022.Repository.AppointmentSchedulingHistoryRepository;
import groupJASS.ISA_2022.Repository.StaffRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentSchedulingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class AppointmentSchedulingHistoryService implements IAppointmentSchedulingHistoryService {
    private final BloodDonorService _bloodDonorService;
    private final AppointmentSchedulingHistoryRepository _appointmentSchedulingHistoryRepository;
    private final StaffRepository _staffRepository;
    private final AccountRepository _accountRepository;

    @Autowired
    public AppointmentSchedulingHistoryService(
            AppointmentSchedulingHistoryRepository appointmentSchedulingHistoryRepository,
            StaffRepository staffRepository,
            AccountRepository accountRepository,
            BloodDonorService bloodDonorService) {
        _appointmentSchedulingHistoryRepository = appointmentSchedulingHistoryRepository;
        _staffRepository = staffRepository;
        _accountRepository = accountRepository;
        _bloodDonorService = bloodDonorService;
    }

    @Override
    public Iterable<AppointmentSchedulingHistory> findAll() {
        return _appointmentSchedulingHistoryRepository.findAll();
    }

    @Override
    public AppointmentSchedulingHistory findById(UUID id) {
        if (_appointmentSchedulingHistoryRepository.findById(id).isPresent()) {

            return _appointmentSchedulingHistoryRepository.findById(id).get();
        }

        throw new NotFoundException("AppointmentSchedulingHistory not found");
    }

    @Override
    public AppointmentSchedulingHistory save(AppointmentSchedulingHistory entity) throws BadRequestException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        return _appointmentSchedulingHistoryRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _appointmentSchedulingHistoryRepository.deleteById(id);
    }


    @Override
    public Page<AppointmentSchedulingHistory> bloodDonorPendingAppointments(UUID personId, int pageSize, int page, String field, String sort) throws SortNotFoundException {


        return _appointmentSchedulingHistoryRepository.searchPendingBy(personId, PageRequest.of(page, pageSize));

    }

    @Override
    public List<AppointmentSchedulingHistory> getByDonorAndCenterId(UUID bloodDonorId, UUID bloodCenterId) {
        return _appointmentSchedulingHistoryRepository.getByDonorAndCenterId(bloodDonorId, bloodCenterId);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void staffCancelAppointment(BloodDonor donor, boolean showedUp,
                                       UUID appointmentHistoryId) throws Exception {

        _bloodDonorService.updatePenalties(donor, showedUp);
        AppointmentSchedulingHistory appointment = findById(appointmentHistoryId);
        appointment.setStatus(AppointmentSchedulingConfirmationStatus.REJECTED);
        save(appointment);
    }

    @Transactional(rollbackOn = Exception.class)
    public void cancelAppointment(UUID appointmentId, UUID bloodDonorId) throws Exception {
        var ashs = _appointmentSchedulingHistoryRepository.nes(appointmentId, bloodDonorId);
        var ash = ashs.get(0);
        if (ash == null) {
            throw new Exception("This blood donor has not schedueled this appointment.");
        }
        if (ash.getStatus() == AppointmentSchedulingConfirmationStatus.CANCELED) {
            throw new Exception("This blood donor has already canceled this appoitnment");
        }
        if (ash.getStatus() == AppointmentSchedulingConfirmationStatus.REJECTED) {
            throw new Exception("This blood donor has been rejected and can not cancel the appoitnment.");
        }
        if (ash.getStatus() == AppointmentSchedulingConfirmationStatus.PROCESSED) {
            throw new Exception("This blood donor has been processed and can not cancel the appoitnment.");
        }

        System.out.println(LocalDateTime.now().plusDays(24));
        if (ash.getAppointment().getTime().getStartTime().isBefore(LocalDateTime.now().plusDays(24))) {
            throw new Exception("The appointment can not be canceled 24 hours before it starts.");
        }
        ash.setStatus(AppointmentSchedulingConfirmationStatus.CANCELED);
        _appointmentSchedulingHistoryRepository.save(ash);
    }

    @Override
    public Page<AppointmentSchedulingHistory> getAllByBloodDonor_Id(UUID BloodDonorId, int page, int pageSize, String sort, String filter) throws SortNotFoundException {
        Page<AppointmentSchedulingHistory> pageToSend;
        AppointmentSchedulingConfirmationStatus f = switch (filter) {
            case "PENDING" -> AppointmentSchedulingConfirmationStatus.PENDING;
            case "PROCESSED" -> AppointmentSchedulingConfirmationStatus.PROCESSED;
            case "REJECTED" -> AppointmentSchedulingConfirmationStatus.REJECTED;
            case "CANCELED" -> AppointmentSchedulingConfirmationStatus.CANCELED;
            default -> AppointmentSchedulingConfirmationStatus.PENDING;
        };
        if (sort.isBlank()) {
            pageToSend = _appointmentSchedulingHistoryRepository.sortFilter(f, BloodDonorId, PageRequest.of(page, pageSize));
        } else if (sort.equals("asc")) {
            pageToSend = _appointmentSchedulingHistoryRepository.sortFilter(f, BloodDonorId, PageRequest.of(page, pageSize)
                    .withSort(Sort.by(Sort.Direction.ASC, "issuingDate")));
        } else if (sort.equals("desc")) {
            pageToSend = _appointmentSchedulingHistoryRepository.sortFilter(f, BloodDonorId, PageRequest.of(page, pageSize)
                    .withSort(Sort.by(Sort.Direction.DESC, "issuingDate")));
        } else {
            throw new SortNotFoundException("This sort type doesn't exist");
        }
        return pageToSend;
    }

    @Override
    public boolean exists(UUID id) {
        return _appointmentSchedulingHistoryRepository.existsById(id);
    }

    @Override
    //If everything is ok it returns null, otherwise returns name of blood center appointment should be held
    public Optional<String> takesPlaceAtBloodCenter(UUID appointmentId, Principal principal) {
        UUID staffId = _accountRepository.findByEmail(principal.getName()).getPersonId();
        Optional<Staff> requestingStaff = _staffRepository.findById(staffId);
        if (!requestingStaff.isPresent()) {
            return Optional.ofNullable(null);
        }

        UUID requestingBloodCenterId = requestingStaff.get().getBloodCenter().getId();
        BloodCenter appointmentsBloodCenter = _appointmentSchedulingHistoryRepository.findById(appointmentId).get().getAppointment().getBloodCenter();

        if (requestingBloodCenterId.equals(appointmentsBloodCenter.getId())) {
            return Optional.ofNullable(null);
        }
        return Optional.ofNullable(appointmentsBloodCenter.getName());
    }
}
