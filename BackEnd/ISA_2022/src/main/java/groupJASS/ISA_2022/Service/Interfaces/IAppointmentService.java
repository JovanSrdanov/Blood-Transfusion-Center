package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.DTO.Appointment.AvailablePredefinedDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Appointment;
import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import groupJASS.ISA_2022.Model.DateRange;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IAppointmentService extends ICrudService<Appointment> {
    List<Appointment> findAllThatOverlap();

    List<DateRange> findFreeSlotsForStaffId(UUID staffId, DateRange bigRange, int duration);

    List<DateRange> findFreeSlotsForStaffIds(List<UUID> staffIds, LocalDateTime date, int duration)
            throws BadRequestException;

    Appointment predefine(DateRange dateRange, List<UUID> staffIds, UUID staffAdminId) throws BadRequestException;

    List<AvailablePredefinedDto> findAvailableAppointmentsForDonor(UUID donorId, UUID centerId);

    AppointmentSchedulingHistory scheduleAppointment(UUID donorId, UUID appointmentId);

    Page<Appointment> getPremadeAppointmentsForBloodCenter(UUID centerId, int page, int pageSize, String sort)
            throws SortNotFoundException;
}
