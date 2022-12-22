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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("appointment-report")
public class AppointmentReportController {
    private final IAppointmentReportService _appointmentReportService;
    private final IEquipmentService _equipmentService;
    private final IAppointmentSchedulingHistoryService _appointmentSchedulingHistoryService;

    @Autowired
    public AppointmentReportController(
            IAppointmentReportService _appointmentReportService,
            IEquipmentService equipmentService,
            IAppointmentSchedulingHistoryService appointmentSchedulingHistoryService) {
        this._appointmentReportService = _appointmentReportService;
        this._equipmentService = equipmentService;
        this._appointmentSchedulingHistoryService = appointmentSchedulingHistoryService;
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("create-report")
    public ResponseEntity<?> createAppointment(
            @Validated @RequestBody AppointmentReportCreate report
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
}
