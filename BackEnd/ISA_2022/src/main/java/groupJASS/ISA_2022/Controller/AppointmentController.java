package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Appointment.*;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.Appointment;
import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import groupJASS.ISA_2022.Model.DateRange;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
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
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> findAll(@RequestBody AvailableSlotsDto dto, Principal account) {
        Account a = _accountService.findAccountByEmail(account.getName());
        List<DateRange> res = null;
        try {
            res = _appointmentService.findFreeSlotsForStaffIds(dto.getStaffIds(), dto.getDate(),
                    dto.getDuration());
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/predefine")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> predefine(@RequestBody PredefineAppointmentDto dto, Principal account) {
        Account a = _accountService.findAccountByEmail(account.getName());
        Appointment res = null;
        try {
            res = _appointmentService.predefine(dto.getDateRange(), dto.getStaffIds(), a.getPersonId(), true);
        }
        catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/available-donor/{cid}")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<List<AvailablePredefinedDto>> findAvailableAppointmentsForDonor(@PathVariable UUID cid, Principal account) {
        Account a = _accountService.findAccountByEmail(account.getName());
        var res = (List<AvailablePredefinedDto>)_appointmentService.findAvailableAppointmentsForDonor(a.getPersonId(), cid);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/schedulePredefine/{appid}")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<AppointmentSchedulingHistory> scheduleAppointment(@PathVariable UUID appid, Principal account) {
        Account a = _accountService.findAccountByEmail(account.getName());
        var res = (AppointmentSchedulingHistory)_appointmentService.scheduleAppointment(a.getPersonId(), appid);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/custom-available")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> findCustomAvailableAppointments(@RequestBody LocalDateTime time, Principal account) {
        Account a = _accountService.findAccountByEmail(account.getName());
        var res = (List<AvailableCustomAppointmentsDto>)_appointmentService.findCustomAvailableAppointments(a.getPersonId(), time);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/schedule-custom")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> scheduleAppointment(@RequestBody ScheduleCustomAppointmentDto dto, Principal account) {
        Account a = _accountService.findAccountByEmail(account.getName());
        AppointmentSchedulingHistory res = null;
        try {
            res = _appointmentService.scheduleCustomAppointmetn(
                    a.getPersonId(),
                    dto.getTime(),
                    dto.getStaffId());
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }



}
