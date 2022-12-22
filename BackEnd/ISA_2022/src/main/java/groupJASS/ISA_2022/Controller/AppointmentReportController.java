package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.AppointmentReport.AppointmentReportCreate;
import groupJASS.ISA_2022.Model.AppointmentReport;
import groupJASS.ISA_2022.Model.AppointmentReportEquipmentQuantity;
import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentReportService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentSchedulingHistoryService;
import groupJASS.ISA_2022.Service.Interfaces.IEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import org.webjars.NotFoundException;

import java.util.HashSet;
import java.util.Set;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("appointment-report")
public class AppointmentReportController {
    private final IAppointmentReportService _appointmentReportService;
    private final IEquipmentService _equipmentService;
    private final IAppointmentSchedulingHistoryService _appointmentSchedulingHistoryService;
    private final IAccountService _accountService;

    @Autowired
    public AppointmentReportController(
            IAppointmentReportService _appointmentReportService,
            IEquipmentService equipmentService,
            IAppointmentSchedulingHistoryService appointmentSchedulingHistoryService,
            IAccountService accountService) {
        this._appointmentReportService = _appointmentReportService;
        this._equipmentService = equipmentService;
        this._appointmentSchedulingHistoryService = appointmentSchedulingHistoryService;
        _accountService = accountService;
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("create-report")
    public ResponseEntity<?> createAppointment(
            @RequestBody AppointmentReportCreate report
            ) {
        try {
            //TODO AppointmentReportEquipmentQuantity uvezati
            AppointmentSchedulingHistory history =
                    _appointmentSchedulingHistoryService.findById(report.getAppointmentHistoryId());
            AppointmentReport newReport = new AppointmentReport();
            newReport.setText(report.getText());
            newReport.setAppointmentSchedulingHistory(history);
            //mozda nije dobro
            newReport.setUsedEquipment(new HashSet<AppointmentReportEquipmentQuantity>());
            _appointmentReportService.save(newReport);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
