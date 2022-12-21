package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.Model.DateRange;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        var res = (List<DateRange>) _appointmentService.findFreeSlotsForStaffIds(UUID.fromString(id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/ids")
    public ResponseEntity<List<DateRange>> findAll(@RequestBody ArrayList<String> ids) {
        var res = (List<DateRange>) _appointmentService.findFreeSlotsForStaffIds(ids);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
