package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    @Query("select a from Appointment a where a.id in " +
            "(select a2.id from Appointment a2 where " +
            "(a2.bloodCenter.id = '417c9b36-251a-4483-bfbf-abd3df786d96' and " +
            "a2.time.startTime <= '2022-12-21 00:02:00.000' and '2022-12-21 00:01:00.000' <= a2.time.endTime))")
    List<Appointment> findAllThatOverlap();

    @Query(value = "select *" +
            "from appointment_staff s join appointment a on s.appointment_id = a.id " +
            "where s.staff_id = :id " +
            "and a.start_time >= :startTime and a.end_time <= :endTime " +
            "order by a.start_time asc",
            nativeQuery = true)
    List<Appointment> findTakenChunksByStaffId(@Param("id") UUID staff_id,
                                               @Param("startTime") LocalDateTime startTime,
                                               @Param("endTime") LocalDateTime endTime);

    @Query("select a from Appointment a " +
            "inner join fetch AppointmentSchedulingHistory  ash on ash.appointment.id =a.id" +
            " where   a.bloodCenter.id = :centerId and a.isPremade=true  ")
    Page<Appointment> searchBy(@Param("centerId") UUID centerId, Pageable of);
}
