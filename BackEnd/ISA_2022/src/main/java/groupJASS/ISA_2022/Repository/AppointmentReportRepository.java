package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.AppointmentReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AppointmentReportRepository extends JpaRepository<AppointmentReport, UUID> {


    @Query("select ar from AppointmentReport ar " +
            "left outer join fetch Appointment a on ar.appointmentSchedulingHistory.appointment.id = a.id " +
            "where ar.appointmentSchedulingHistory.bloodDonor.id = :bloodDonorId ")
    Page<AppointmentReport> searchBy(UUID bloodDonorId, PageRequest of);

    @Query("select ar from AppointmentReport ar " +
            "left outer join fetch Appointment a on ar.appointmentSchedulingHistory.appointment.id = a.id " +
            "where ar.appointmentSchedulingHistory.bloodDonor.id = :bloodDonorId " +
            "order by a.time.startTime")
    Page<AppointmentReport> searchByAsc(UUID bloodDonorId, PageRequest start_time);

    @Query("select ar from AppointmentReport ar " +
            "left outer join fetch Appointment a on ar.appointmentSchedulingHistory.appointment.id = a.id " +
            "where ar.appointmentSchedulingHistory.bloodDonor.id = :bloodDonorId " +
            "order by a.time.startTime desc ")
    Page<AppointmentReport> searchByDesc(UUID bloodDonorId, PageRequest start_time);
}
