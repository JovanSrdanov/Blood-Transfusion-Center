package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Appointment.AppointmentCancelation;
import groupJASS.ISA_2022.DTO.Appointment.BloodDonorAppointmentsDTO;
import groupJASS.ISA_2022.DTO.Appointment.BloodDonorAppointmentsForCenter;
import groupJASS.ISA_2022.DTO.PageEntityDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Model.Staff;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentSchedulingHistoryService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodDonorService;
import groupJASS.ISA_2022.Service.Interfaces.IStaffService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("appointment_scheduling_history")
public class AppointmentSchedulingHistoryController {

    private final IAccountService _accountService;
    private final IAppointmentSchedulingHistoryService _appointmentSchedulingHistoryRepository;
    private final IStaffService _staffService;
    private final IBloodDonorService _bloodDonorService;

    @Autowired
    public AppointmentSchedulingHistoryController(IAccountService accountService,
                                                  IAppointmentSchedulingHistoryService appointmentSchedulingHistoryRepository,
                                                  IStaffService staffService,
                                                  IBloodDonorService blooDonorService) {
        _accountService = accountService;
        _staffService = staffService;
        _appointmentSchedulingHistoryRepository = appointmentSchedulingHistoryRepository;
        _bloodDonorService = blooDonorService;
    }

    @GetMapping("/blood-donor-appointments")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> bloodDonorAppointments(Principal account,
                                                    @RequestParam(name = "page") int page,
                                                    @RequestParam(name = "pageSize") int pageSize,
                                                    @RequestParam(name = "field") String field,
                                                    @RequestParam(name = "sort") String sort) {
        Account a = _accountService.findAccountByEmail(account.getName());
        Page<AppointmentSchedulingHistory> entities = _appointmentSchedulingHistoryRepository.bloodDonorPendingAppointments(a.getPersonId(), page, field, sort);
        List<BloodDonorAppointmentsDTO> content = new ArrayList<>();
        //PageEntityDto<List<BloodDonorAppointmentsDTO>> pageDTO = new PageEntityDto<>(content, (int) entities.getTotalElements());
        return new ResponseEntity<>(new PageEntityDto<List<BloodDonorAppointmentsDTO>>(), HttpStatus.OK);
    }

    @GetMapping("/blood-donor-appointments-for-centre/{bloodDonorId}")
    @PreAuthorize("hasRole('STAFF')")   //nisam  siguran dal je ovo dobro
    public ResponseEntity<?> bloodDonorAppointmentsForCentre(Principal account,
                                                             @PathVariable("bloodDonorId") UUID bloodDonorId) {
        List<BloodDonorAppointmentsForCenter> result = new ArrayList<>();
        Account a = _accountService.findAccountByEmail(account.getName());
        UUID personId = a.getPersonId();
        Staff staff = _staffService.findById(personId);
        //Staff staff = _staffService.findById(_accountService.findAccountByEmail(account.getName()).getPersonId());
        //Staff staff = new Staff();

        //dobijeno hql upitom
        List<AppointmentSchedulingHistory> appointmentHistories =
                _appointmentSchedulingHistoryRepository.getByDonorAndCenterId(bloodDonorId,
                        staff.getBloodCenter().getId());

        //TODO podsetiti se mapera
        for(AppointmentSchedulingHistory item: appointmentHistories) {
            result.add(new BloodDonorAppointmentsForCenter(item));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //TODO stavi da bude transactional
    @PutMapping("/cancel-appointment")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> cancelAppointment(@Validated @RequestBody AppointmentCancelation dto) {
        try {
            BloodDonor donor = _bloodDonorService.findById(dto.getBloodDonorId());
            _bloodDonorService.updatePenalties(donor, dto.isShowedUp());
            _appointmentSchedulingHistoryRepository.cancelAppointment(dto.getAppointmentHistoryId());

            return new ResponseEntity<>(HttpStatus.OK);

        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
