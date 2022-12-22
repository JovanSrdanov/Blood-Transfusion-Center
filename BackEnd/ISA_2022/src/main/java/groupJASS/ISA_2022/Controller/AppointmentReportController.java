package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Appointment.AppointmentReportInfoDTO;
import groupJASS.ISA_2022.DTO.PageEntityDto;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.AppointmentReport;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentReportService;
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
import java.util.UUID;

@RestController
@RequestMapping("appointment-report")
public class AppointmentReportController {
    private final IAppointmentReportService _appointmentReportService;
    private final IAccountService _accountService;

    public AppointmentReportController(IAppointmentReportService _appointmentReportService, IAccountService accountService) {
        this._appointmentReportService = _appointmentReportService;
        _accountService = accountService;
    }

    @GetMapping("/my-reports")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> bloodDonorAppointments(
            Principal account,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "pageSize") int pageSize,
            @RequestParam(name = "field") String field,
            @RequestParam(name = "sort") String sort) throws SortNotFoundException {

        try {
            UUID BloodDonorId = _accountService.findAccountByEmail(account.getName()).getPersonId();

            Page<AppointmentReport> entities = _appointmentReportService.getBloodDonorReports(BloodDonorId, page, pageSize, sort);
            List<AppointmentReportInfoDTO> content = new ArrayList<>();
            for (var e : entities) {
                content.add(new AppointmentReportInfoDTO(e.getId(),
                        e.getAppointmentSchedulingHistory().getAppointment().getTime().getStartTime(),
                        e.getAppointmentSchedulingHistory().getAppointment().getTime().calcaulateDurationMinutes(), e.getText()));

            }
            PageEntityDto<List<AppointmentReportInfoDTO>> pageDTO = new PageEntityDto<>(content,
                    (int) entities.getTotalElements());
            return new ResponseEntity<>(pageDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
