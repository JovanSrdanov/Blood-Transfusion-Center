package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // stoji nula jer je to pending
//    @Query("select ash from AppointmentSchedulingHistory ash left join fetch ash.appointment left join fetch  ash.bloodDonor left join fetch  ash.appointment.bloodCenter" +
//            " where ash.status = '0' and ash.bloodDonor.id = :bloodDonorId ")
    @Query("select ash from AppointmentSchedulingHistory ash " +
            "left join fetch Appointment  a on ash.appointment.id = a.id " +
            "left join fetch BloodCenter bc on ash.appointment.bloodCenter.id = bc.id" +
            " where ash.status = '0' and ash.bloodDonor.id = :bloodDonorId " +
            " order by ash.appointment.time.startTime")
    Page<AppointmentSchedulingHistory> searchPendingBy(@Param("bloodDonorId") UUID bloodDonorId, Pageable pageable);

    @Query("select ash from AppointmentSchedulingHistory ash " +
            "where ash.appointment.id = :appointmentId and ash.bloodDonor.id = :bloodDonorId")
    List<AppointmentSchedulingHistory> nes(@Param("appointmentId") UUID appointmentId, @Param("bloodDonorId") UUID bloodDonorId);

    @Query("select ash from AppointmentSchedulingHistory ash " +
            "left join fetch Appointment  a on ash.appointment.id = a.id " +
            "left join fetch BloodCenter bc on ash.appointment.bloodCenter.id = bc.id" +
            " where  ash.bloodDonor.id = :id " +
            " order by ash.appointment.time.startTime")
    List<AppointmentSchedulingHistory> gascina(@Param("id") UUID id);


    boolean existsById(UUID id);
}
