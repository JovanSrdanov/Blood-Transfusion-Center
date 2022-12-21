package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.Appointment;
import groupJASS.ISA_2022.Model.DateRange;

import java.util.List;

public interface IAppointmentService extends ICrudService<Appointment>{
    List<Appointment> findAllThatOverlap();
    List<DateRange> findDefinedByStaffId(String staffId);
}
