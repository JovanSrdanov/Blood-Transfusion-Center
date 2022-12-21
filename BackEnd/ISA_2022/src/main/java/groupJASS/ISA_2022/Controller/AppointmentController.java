package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.Model.DateRange;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("appointment")
public class AppointmentController {
    private final IAppointmentService _appointmentService;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService) {
        this._appointmentService = appointmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<DateRange>> findAll(@PathVariable String id) {

        var res = (List<DateRange>) _appointmentService.findDefinedByStaffId(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
