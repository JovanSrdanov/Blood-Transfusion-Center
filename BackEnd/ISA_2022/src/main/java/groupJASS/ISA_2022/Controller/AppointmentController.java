package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Appointment.AvailableSlotsDto;
import groupJASS.ISA_2022.DTO.Appointment.PredefineAppointmentDto;
import groupJASS.ISA_2022.Model.Appointment;
import groupJASS.ISA_2022.Model.DateRange;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("appointment")
public class AppointmentController {
    private final IAppointmentService _appointmentService;
    private final IAccountService _accountService;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService, IAccountService accountService) {
        this._appointmentService = appointmentService;
        _accountService = accountService;
    }

    @PostMapping("/available-admin")
    public ResponseEntity<List<DateRange>> findAll(@RequestBody AvailableSlotsDto dto) {
        var res = (List<DateRange>) _appointmentService.findFreeSlotsForStaffIds(dto.getStaffIds(), dto.getDate(),
                dto.getDuration());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/predefine")
    public ResponseEntity<Appointment> predefine(@RequestBody PredefineAppointmentDto dto) {
        var res = (Appointment) _appointmentService.predefine(dto.getDateRange(), dto.getStaffIds());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
