package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.Appointment;
import groupJASS.ISA_2022.Model.DateRange;

import java.util.List;
import java.util.UUID;

public interface IAppointmentService extends ICrudService<Appointment>{
    List<Appointment> findAllThatOverlap();
    List<DateRange> findFreeSlotsForStaffIds(UUID staffId);

    List<DateRange> findFreeSlotsForStaffIds(List<String> staffIds);
}
