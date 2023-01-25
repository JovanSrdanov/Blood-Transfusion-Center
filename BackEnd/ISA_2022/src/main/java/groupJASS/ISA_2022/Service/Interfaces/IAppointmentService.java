package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.DTO.Appointment.AvailableCustomAppointmentsDto;
import groupJASS.ISA_2022.DTO.Appointment.AvailablePredefinedDto;
import groupJASS.ISA_2022.DTO.Appointment.PredefinedInCustomTimeDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.Appointment;
import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import groupJASS.ISA_2022.Model.DateRange;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IAppointmentService extends ICrudService<Appointment> {
    List<Appointment> findAllThatOverlap();

    List<DateRange> findFreeChunksForStaffId(UUID staffId, DateRange bigRange);

    List<DateRange> findFreeSlotsForStaffIds(List<UUID> staffIds, LocalDateTime date, int duration)
            throws BadRequestException;

    Appointment predefine(DateRange dateRange, List<UUID> staffIds, UUID staffAdminId, boolean isPredef) throws BadRequestException;

    List<AvailablePredefinedDto> findAvailableAppointmentsForDonor(UUID donorId, UUID centerId);

    AppointmentSchedulingHistory scheduleAppointment(UUID donorId, UUID appointmentId);

    AppointmentSchedulingHistory scheduleCustomAppointment(UUID donorId, LocalDateTime time, UUID staffId) throws BadRequestException;

    List<AvailableCustomAppointmentsDto> findCustomAvailableAppointments(UUID donorId, LocalDateTime time);


    Page<Appointment> getPremadeAppointmentsForBloodCenter(UUID centerId, UUID donorId, int page, int pageSize, String sort)
            throws SortNotFoundException;

    void sendScheduleConfirmation(Appointment appointment, String email, UUID ASHId);
    List<PredefinedInCustomTimeDto>  findAllByTimeStartTime(LocalDateTime startTime);
}
