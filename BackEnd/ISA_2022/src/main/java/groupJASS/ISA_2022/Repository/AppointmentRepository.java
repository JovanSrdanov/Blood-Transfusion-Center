package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
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
            "and a.id not in (select h.appointment_id from appointment_scheduling_history h " +
            "join appointment a2 on a2.id = h.appointment_id " +
            "where h.status = 3 and a2.is_premade = false)" +
            "and a.start_time >= :startTime and a.end_time <= :endTime " +
            "order by a.start_time asc", nativeQuery = true)
    List<Appointment> findTakenChunksByStaffId(@Param("id") UUID staff_id,
                                               @Param("startTime") LocalDateTime startTime,
                                               @Param("endTime") LocalDateTime endTime);

    @Query(value = "select * from appointment a " +
            "where a.is_premade = true " +
            "and a.blood_center_id = :centerId " +
            "and a.id not in ( " +
            "select h.appointment_id from appointment_scheduling_history h " +
            "where h.status != 3 " +
            "or (h.blood_donor_id = :donor_id and h.status = 3)) ", nativeQuery = true)
    Page<Appointment> searchBy(@Param("centerId") UUID centerId, @Param("donor_id") UUID donor_id, Pageable of);

    @Query(value = "select * from appointment a " +
            "where a.is_premade = true " +
            "and a.blood_center_id = :center_id " +
            "and a.id not in ( " +
            "select h.appointment_id from appointment_scheduling_history h " +
            "where h.status != 3 " +
            "or (h.blood_donor_id = :donor_id and h.status = 3)) " +
            "order by a.start_time ",
            nativeQuery = true)
    List<Appointment> findAvailableAppointmentsForDonor(@Param("donor_id") UUID donor_id, @Param("center_id") UUID center_id);

    @Query(value = "select * from " +
                    "appointment a left join appointment_scheduling_history h on a.id = h.appointment_id "+
                    "where (a.is_premade = true or "+
                    "(a.is_premade = false and (h.blood_donor_id is null or (h.status = 3 and h.blood_donor_id != :donor_id)))) "+
                    "and a.blood_center_id = :center_id "+
                    "and a.id not in ( "+
                    "select h1.appointment_id from appointment_scheduling_history h1 "+
                    "where h1.status != 3 "+
                    "or (h1.blood_donor_id = :donor_id and h1.status = 3)) "+
                    "order by a.start_time ",
            nativeQuery = true)
    List<Appointment> findAvailableAppointmentsForDonorIncludingCustom(@Param("donor_id") UUID donor_id, @Param("center_id") UUID center_id);

    @Query(value = "select * from " +
            "appointment a left join appointment_scheduling_history h on a.id = h.appointment_id "+
            "where h is null and a.start_time = :startTime",
            nativeQuery = true)
    List<Appointment> findAllByTimeStartTime(LocalDateTime startTime);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    //Transaction doesn't wait for other transactions to commit, it just throws exception "CannotAcquireLockException"
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
   Appointment save(Appointment appointment);
}
