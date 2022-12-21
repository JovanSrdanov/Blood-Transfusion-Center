package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IAppointmentSchedulingHistoryService extends ICrudService<AppointmentSchedulingHistory> {
    Page<AppointmentSchedulingHistory> bloodDonorPendingAppointments(UUID personId, int page, String field, String sort);
}
