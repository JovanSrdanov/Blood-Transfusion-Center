package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterIncomingAppointmentsDbDto;
import groupJASS.ISA_2022.Model.BloodCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BloodCenterRepository extends JpaRepository<BloodCenter, UUID> {

    @Query("select bc from BloodCenter bc inner join Address a on bc.address.id = a.id " +
            "where lower(bc.name) like CONCAT('%',lower(:s),'%') or lower(a.city) like CONCAT('%',lower(:s),'%')")
    Page<BloodCenter> searchBy(@Param("s") String s, Pageable pageable);


    @Query(value = "select a.start_time as startTime, a.end_time as endTime , bd.\"name\" as name , bd.surname as surname " +
            "from appointment_scheduling_history ash "+
            "left join appointment a on ash.appointment_id = a.id "+
            "left join blood_center bc on a.blood_center_id  = bc.id "+
            "left join blood_donor bd on ash.blood_donor_id = bd.id "+
            "where bc.id = '5f29fc03-f6bd-49a6-9f41-c2ca229de403' and a.start_time  >  now()", nativeQuery = true)
    List<Object[]> getIncomingAppointmentsForBloodCenter(@Param("bloodCenterId") UUID bloodCenterId);
}


