package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterIncomingAppointmentDto;
import groupJASS.ISA_2022.Model.BloodCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BloodCenterRepository extends JpaRepository<BloodCenter, UUID> {

    @Query("select bc from BloodCenter bc inner join Address a on bc.address.id = a.id " +
            "where lower(bc.name) like CONCAT('%',lower(:s),'%') or lower(a.city) like CONCAT('%',lower(:s),'%')")
    Page<BloodCenter> searchBy(@Param("s") String s, Pageable pageable);


    @Query("select new groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterIncomingAppointmentDto(a.time.startTime, a.time.endTime, bd.name, bd.surname)" +
            " from AppointmentSchedulingHistory ash " +
            "left join Appointment a on ash.appointment.id = a.id " +
            "left join BloodCenter  bc on a.bloodCenter.id = bc.id " +
            "left join BloodDonor bd on ash.bloodDonor.id = bd.id " +
            "where bc.id = :bloodCenterId and a.time.startTime > :now  ")
    List<BloodCenterIncomingAppointmentDto> getIncomingAppointments(@Param("bloodCenterId") UUID bloodCenterId, @Param("now")LocalDateTime now);
    //Curent time has to be passed because hqls current_time() has problems with timezones
}


