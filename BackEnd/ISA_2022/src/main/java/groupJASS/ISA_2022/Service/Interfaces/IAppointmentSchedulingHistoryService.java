package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IAppointmentSchedulingHistoryService extends ICrudService<AppointmentSchedulingHistory> {
    Page<AppointmentSchedulingHistory> bloodDonorPendingAppointments(UUID personId, int page, String field, String sort);
    List<AppointmentSchedulingHistory> getByDonorAndCenterId(UUID bloodDonorId, UUID bloodCenterId);
    AppointmentSchedulingHistory cancelAppointment(UUID appointmentHistoryId) throws BadRequestException;

    Page<AppointmentSchedulingHistory> bloodDonorPendingAppointments(UUID personId, int pageSize, int page, String field, String sort) throws SortNotFoundException;

    void cancelAppointment(UUID appointmentId, UUID bloodDonorId) throws Exception;
}
