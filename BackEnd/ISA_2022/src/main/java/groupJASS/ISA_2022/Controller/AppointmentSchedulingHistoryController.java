package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Appointment.BloodDonorAppointmentsDTO;
import groupJASS.ISA_2022.DTO.PageEntityDto;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentSchedulingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("appointment_scheduling_history")
public class AppointmentSchedulingHistoryController {

    private final IAccountService _accountService;
    private final IAppointmentSchedulingHistoryService _appointmentSchedulingHistoryRepository;

    @Autowired
    public AppointmentSchedulingHistoryController(IAccountService accountService, IAppointmentSchedulingHistoryService appointmentSchedulingHistoryRepository) {
        _accountService = accountService;
        _appointmentSchedulingHistoryRepository = appointmentSchedulingHistoryRepository;
    }

    @GetMapping("/blood-donor-appointments")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> bloodDonorAppointments(Principal account,
                                                    @RequestParam(name = "page") int page,
                                                    @RequestParam(name = "pageSize") int pageSize,
                                                    @RequestParam(name = "field") String field,
                                                    @RequestParam(name = "sort") String sort) throws SortNotFoundException {
        Account a = _accountService.findAccountByEmail(account.getName());
        Page<AppointmentSchedulingHistory> entities = _appointmentSchedulingHistoryRepository.bloodDonorPendingAppointments(a.getPersonId(), pageSize, page, field, sort);
        List<BloodDonorAppointmentsDTO> content = new ArrayList<>();
        for (var e : entities) {
            content.add(new BloodDonorAppointmentsDTO(e.getAppointment().getId(), e.getAppointment().getBloodCenter().getName(), e.getAppointment().getTime().getStartTime(), e.getAppointment().getTime().getStartTime(), e.getAppointment().getTime().calcaulateDurationMinutes()));
        }

        PageEntityDto<List<BloodDonorAppointmentsDTO>> pageDTO = new PageEntityDto<>(content, (int) entities.getTotalElements());
        return new ResponseEntity<>(pageDTO, HttpStatus.OK);
    }

    @GetMapping("/cancel-appointment/{appointmentId}")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> cancelAppointment(@PathVariable UUID appointmentId, Principal account) {
        try {
            UUID bloodDonorId = _accountService.findAccountByEmail(account.getName()).getPersonId();
            _appointmentSchedulingHistoryRepository.cancelAppointment(appointmentId, bloodDonorId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
