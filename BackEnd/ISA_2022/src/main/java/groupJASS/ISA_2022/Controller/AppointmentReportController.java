package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.Service.Interfaces.IAppointmentReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appointment-report")
public class AppointmentReportController {
    private final IAppointmentReportService _appointmentReportService;

    public AppointmentReportController(IAppointmentReportService _appointmentReportService) {
        this._appointmentReportService = _appointmentReportService;
    }
    
}
