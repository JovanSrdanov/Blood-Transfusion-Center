package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import groupJASS.ISA_2022.Model.BloodDonor;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAppointmentSchedulingHistoryService extends ICrudService<AppointmentSchedulingHistory> {
    List<AppointmentSchedulingHistory> getByDonorAndCenterId(UUID bloodDonorId, UUID bloodCenterId);

    void staffCancelAppointment(BloodDonor donor, boolean showedUp,
                                UUID appointmentHistoryId) throws Exception;

    Page<AppointmentSchedulingHistory> bloodDonorPendingAppointments(UUID personId, int pageSize, int page, String field, String sort) throws SortNotFoundException;

    void cancelAppointment(UUID appointmentId, UUID bloodDonorId) throws Exception;


    Page<AppointmentSchedulingHistory> getAllByBloodDonor_Id(UUID id, int page, int pageSize, String sort, String filter) throws SortNotFoundException;

    boolean exists(UUID id);

    Optional<String> takesPlaceAtBloodCenter(UUID appointmentId, Principal principal);
}
