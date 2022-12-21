package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Appointment.BloodDonorAppointmentsDTO;
import groupJASS.ISA_2022.DTO.PageEntityDto;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentSchedulingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
                                                    @RequestParam(name = "sort") String sort) {
        Account a = _accountService.findAccountByEmail(account.getName());
        Page<AppointmentSchedulingHistory> entities = _appointmentSchedulingHistoryRepository.bloodDonorPendingAppointments(a.getPersonId(), page, field, sort);
        List<BloodDonorAppointmentsDTO> content = new ArrayList<>();
        //PageEntityDto<List<BloodDonorAppointmentsDTO>> pageDTO = new PageEntityDto<>(content, (int) entities.getTotalElements());
        return new ResponseEntity<>(new PageEntityDto<List<BloodDonorAppointmentsDTO>>(), HttpStatus.OK);
    }


}
