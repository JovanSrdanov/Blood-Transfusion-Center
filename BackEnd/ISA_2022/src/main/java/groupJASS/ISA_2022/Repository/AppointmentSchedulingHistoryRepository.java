package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Primary
public interface AppointmentSchedulingHistoryRepository extends JpaRepository<AppointmentSchedulingHistory, UUID> {
    @Query("select ash from AppointmentSchedulingHistory ash " +
    "left outer join fetch Appointment a on ash.appointment.id = a.id " +
    "left join fetch BloodCenter bc on ash.appointment.bloodCenter.id = bc.id " +
    "where ash.status = '0' and ash.bloodDonor.id = :bloodDonorId and ash.appointment.bloodCenter.id = :bloodCenterId")
    List<AppointmentSchedulingHistory> getByDonorAndCenterId(@Param("bloodDonorId") UUID bloodDonorId,
                                                             @Param("bloodCenterId") UUID bloodCenterId);
}
