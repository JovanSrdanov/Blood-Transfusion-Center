package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Appointment.AvailableSlotsDto;
import groupJASS.ISA_2022.DTO.Appointment.PredefineAppointmentDto;
import groupJASS.ISA_2022.DTO.Appointment.PremadeAppointmentDTO;
import groupJASS.ISA_2022.DTO.PageEntityDto;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.Appointment;
import groupJASS.ISA_2022.Model.DateRange;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
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

    @GetMapping("/premadeAppointments/{centerId}")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> bloodDonorAppointments(
            @PathVariable UUID centerId,
            Principal account,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "pageSize") int pageSize,
            @RequestParam(name = "field") String field,
            @RequestParam(name = "sort") String sort) throws SortNotFoundException {


        try {
            UUID BloodDonorId = _accountService.findAccountByEmail(account.getName()).getPersonId();

            Page<Appointment> entities = _appointmentService.getPremadeAppointmentsForBloodCenter(centerId, page, pageSize, sort);
            List<PremadeAppointmentDTO> content = new ArrayList<>();
            for (var e : entities) {
                content.add(new PremadeAppointmentDTO(e.getId(), e.getTime().getStartTime(), e.getTime().getDurationMinutes()));
            }
            PageEntityDto<List<PremadeAppointmentDTO>> pageDTO = new PageEntityDto<>(content, (int) entities.getTotalElements());
            return new ResponseEntity<>(pageDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
