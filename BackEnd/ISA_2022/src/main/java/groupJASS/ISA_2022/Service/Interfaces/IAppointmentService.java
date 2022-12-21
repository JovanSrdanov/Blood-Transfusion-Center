package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.Appointment;
import groupJASS.ISA_2022.Model.DateRange;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IAppointmentService extends ICrudService<Appointment> {
    List<Appointment> findAllThatOverlap();

    List<DateRange> findFreeSlotsForStaffId(UUID staffId, DateRange bigRange, int duration);

    List<DateRange> findFreeSlotsForStaffIds(List<String> staffIds, LocalDateTime date, int duration);

    Appointment predefine(DateRange dateRange, List<UUID> staffIds);


    Page<Appointment> getPremadeAppointmentsForBloodCenter(UUID centerId, int page, int pageSize, String sort) throws SortNotFoundException;
}
