package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Appointment.*;
import groupJASS.ISA_2022.DTO.PageEntityDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Exceptions.QrCodeReadingException;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.Appointment;
import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import groupJASS.ISA_2022.Model.DateRange;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentSchedulingHistoryService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import groupJASS.ISA_2022.Service.Interfaces.IQrCodeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("appointment")
@SecurityRequirement(name = "Bearer Authentication")
public class AppointmentController {
    private final IAppointmentService _appointmentService;
    private final IAccountService _accountService;
    private final IQrCodeService _qrCodeService;
    private  final IAppointmentSchedulingHistoryService _appointmentSchedulingHistoryService;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService, IAccountService accountService,
                                 IQrCodeService qrCodeService, IAppointmentSchedulingHistoryService appointmentSchedulingHistoryService) {
        _appointmentService = appointmentService;
        _accountService = accountService;
        _qrCodeService = qrCodeService;
        _appointmentSchedulingHistoryService = appointmentSchedulingHistoryService;
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
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/available-donor/{cid}")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<List<AvailablePredefinedDto>> findAvailableAppointmentsForDonor(@PathVariable UUID cid,
                                                                                          Principal account) {
        Account a = _accountService.findAccountByEmail(account.getName());
        var res = (List<AvailablePredefinedDto>) _appointmentService.findAvailableAppointmentsForDonor(a.getPersonId(),
                cid);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/schedulePredefine/{appid}")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> scheduleAppointment(@PathVariable UUID appid,
                                                 Principal account) {

        try {

            Account a = _accountService.findAccountByEmail(account.getName());
            var res = (AppointmentSchedulingHistory) _appointmentService.scheduleAppointment(a.getPersonId(), appid);
            _appointmentService.sendScheduleConfirmation(res.getAppointment(), account.getName(), res.getId());
            return new ResponseEntity<>(true, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/custom-available")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> findCustomAvailableAppointments(@RequestBody TimeDto time, Principal account) {
        Account a = _accountService.findAccountByEmail(account.getName());
        var res = (List<AvailableCustomAppointmentsDto>) _appointmentService.findCustomAvailableAppointments(a.getPersonId(), time.getTime());
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
            _appointmentService.sendScheduleConfirmation(res.getAppointment(), account.getName(), res.getId());
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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

            Page<Appointment> entities = _appointmentService.getPremadeAppointmentsForBloodCenter(centerId, BloodDonorId, page,
                    pageSize, sort);
            List<PremadeAppointmentDTO> content = new ArrayList<>();
            for (var e : entities) {
                content.add(new PremadeAppointmentDTO(e.getId(), e.getTime().getStartTime(),
                        e.getTime().calcaulateDurationMinutes()));
            }
            PageEntityDto<List<PremadeAppointmentDTO>> pageDTO = new PageEntityDto<>(content,
                    (int) entities.getTotalElements());
            return new ResponseEntity<>(pageDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(path = "/available-predefined-for-time")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity availablePredefined(@RequestBody TimeDto time) {
        return new ResponseEntity<>(_appointmentService.findAllByTimeStartTime(time.getTime()), HttpStatus.OK);
    }

    @PostMapping(path = "/scan-qr",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity scanQRCode(@RequestParam MultipartFile qrCode, Principal principal)
    {
        try
        {
            UUID appointmentId = _qrCodeService.readAppointmentCode(qrCode);

            if(!_appointmentSchedulingHistoryService.exists(appointmentId))
            {
                return  new ResponseEntity<>("There is no appointment with given code", HttpStatus.NOT_FOUND);
            }

            //Checks if patient came at right blood center
            var correctBloodCenterName = _appointmentSchedulingHistoryService.takesPlaceAtBloodCenter(appointmentId, principal);
            if(correctBloodCenterName.isPresent())
            {
                return  new ResponseEntity<>("Appointment is scheduled in: " + correctBloodCenterName.get(), HttpStatus.BAD_REQUEST);
            }

            AppointmentQrInformationDto dto = new AppointmentQrInformationDto(appointmentId);
            return  new ResponseEntity<>(dto, HttpStatus.OK);
        }
        catch (QrCodeReadingException e){
            return  new ResponseEntity<>("QR code couldn't be read", HttpStatus.BAD_REQUEST);
        }
    }
}
