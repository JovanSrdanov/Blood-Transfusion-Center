package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Appointment.AppointmentCancelation;
import groupJASS.ISA_2022.DTO.Appointment.BloodDonorAppointmentsDTO;
import groupJASS.ISA_2022.DTO.Appointment.BloodDonorAppointmentsForCenter;
import groupJASS.ISA_2022.DTO.Appointment.QrCodeASHDTO;
import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorSearchByNameAndSurnameDto;
import groupJASS.ISA_2022.DTO.PageEntityDto;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Model.Staff;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentSchedulingHistoryService;
import groupJASS.ISA_2022.Service.Interfaces.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("appointment_scheduling_history")
public class AppointmentSchedulingHistoryController {

    private final IAccountService _accountService;
    private final IAppointmentSchedulingHistoryService _appointmentSchedulingHistoryService;
    private final IStaffService _staffService;

    @Autowired
    public AppointmentSchedulingHistoryController(IAccountService accountService,
                                                  IAppointmentSchedulingHistoryService appointmentSchedulingHistoryRepository,
                                                  IStaffService staffService) {
        _accountService = accountService;
        _staffService = staffService;
        _appointmentSchedulingHistoryService = appointmentSchedulingHistoryRepository;
    }

    @GetMapping("fullname/{ashId}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> getDonorFullname(
            @PathVariable("ashId") UUID ashId) {
        try {
            var donor = _appointmentSchedulingHistoryService.findById(ashId)
                    .getBloodDonor();
            var result = new BloodDonorSearchByNameAndSurnameDto();
            result.setName(donor.getName());
            result.setSurname(donor.getSurname());

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/blood-donor-appointments")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> bloodDonorAppointments(Principal account,
                                                    @RequestParam(name = "page") int page,
                                                    @RequestParam(name = "pageSize") int pageSize,
                                                    @RequestParam(name = "field") String field,
                                                    @RequestParam(name = "sort") String sort) throws SortNotFoundException {
        Account a = _accountService.findAccountByEmail(account.getName());
        Page<AppointmentSchedulingHistory> entities = _appointmentSchedulingHistoryService
                .bloodDonorPendingAppointments(a.getPersonId(), pageSize, page, field, sort);
        List<BloodDonorAppointmentsDTO> content = new ArrayList<>();
        for (var e : entities) {
            content.add(new BloodDonorAppointmentsDTO(e.getAppointment().getId(),
                    e.getAppointment().getBloodCenter().getName(), e.getAppointment().getTime().getStartTime(),
                    e.getAppointment().getTime().getStartTime(),
                    e.getAppointment().getTime().calcaulateDurationMinutes()));
        }

        PageEntityDto<List<BloodDonorAppointmentsDTO>> pageDTO = new PageEntityDto<>(content,
                (int) entities.getTotalElements());
        return new ResponseEntity<>(pageDTO, HttpStatus.OK);
    }

    @GetMapping("/blood-donor-appointments-QR")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> bloodDonorAppointmentsQR(Principal account,
                                                      @RequestParam(name = "page") int page,
                                                      @RequestParam(name = "pageSize") int pageSize,
                                                      @RequestParam(name = "sort") String sort,
                                                      @RequestParam(name = "filter") String filter) throws SortNotFoundException {
        try {

            UUID bloodDonorId = _accountService.findAccountByEmail(account.getName()).getPersonId();
            Page<AppointmentSchedulingHistory> ashList = _appointmentSchedulingHistoryService
                    .getAllByBloodDonor_Id(bloodDonorId, page, pageSize, sort, filter);

            List<QrCodeASHDTO> list = new ArrayList<>();
            for (var ash : ashList) {
                String qrCodeText = "Your appointment is scheduled at "
                        + ash.getAppointment().getTime().getStartTime()
                        .format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy."))
                        +
                        " in blood center " + ash.getAppointment().getBloodCenter().getName() + ".\n=====\n" +
                        "Appointment code: " + ash.getId();
                list.add(new QrCodeASHDTO(ash.getId(), qrCodeText, ash.getStatus(), ash.getIssuingDate()));
            }
            PageEntityDto<List<QrCodeASHDTO>> pageDto = new PageEntityDto<>(list, (int) ashList.getTotalElements());
            return new ResponseEntity<>(pageDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cancel-appointment/{appointmentId}")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> cancelAppointment(@PathVariable UUID appointmentId, Principal account) {
        try {
            UUID bloodDonorId = _accountService.findAccountByEmail(account.getName()).getPersonId();
            _appointmentSchedulingHistoryService.cancelAppointment(appointmentId, bloodDonorId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/blood-donor-appointments-for-centre/{bloodDonorId}")
    @PreAuthorize("hasRole('STAFF')") // nisam siguran dal je ovo dobro
    public ResponseEntity<?> bloodDonorAppointmentsForCentre(Principal account,
                                                             @PathVariable("bloodDonorId") UUID bloodDonorId) {
        List<BloodDonorAppointmentsForCenter> result = new ArrayList<>();
        Account a = _accountService.findAccountByEmail(account.getName());
        UUID personId = a.getPersonId();
        Staff staff = _staffService.findById(personId);

        // dobijeno hql upitom
        List<AppointmentSchedulingHistory> appointmentHistories = _appointmentSchedulingHistoryService
                .getByDonorAndCenterId(bloodDonorId,
                        staff.getBloodCenter().getId());

        // TODO podsetiti se mapera
        for (AppointmentSchedulingHistory item : appointmentHistories) {
            result.add(new BloodDonorAppointmentsForCenter(item));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // TODO stavi da bude transactional
    @PutMapping("/cancel-appointment")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> cancelAppointment(@RequestBody AppointmentCancelation dto) {
        try {
            BloodDonor donor = _appointmentSchedulingHistoryService.findById(dto.getAppointmentHistoryId())
                    .getBloodDonor();
            _appointmentSchedulingHistoryService.staffCancelAppointment(
                    donor,
                    dto.isShowedUp(),
                    dto.getAppointmentHistoryId());

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
