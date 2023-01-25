package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorInfoDto;
import groupJASS.ISA_2022.Model.AppointmentSchedulingConfirmationStatus;
import groupJASS.ISA_2022.Model.BloodDonor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BloodDonorRepository extends JpaRepository<BloodDonor, UUID> {
    boolean existsBloodUserByJmbg(String jmbg);

    @Query("select bl from BloodDonor bl left join fetch bl.address a  left join fetch bl.questionnaire q")
    List<BloodDonor> findAllWithAddressAndQuestionnaire();

    Iterable<BloodDonor> findBloodDonorByNameAndSurname(String name, String surname);

    @Query("select  bd from BloodDonor bd" +
            " where upper(bd.name) like CONCAT(upper(:name) ,'%') and" +
            " upper(bd.surname) like CONCAT(upper(:surname) ,'%') ")
    Page<BloodDonor> findByNameAndSurnameForCenterAndStatusIgnoreCase(@Param("name") String name, @Param("surname") String surname, Pageable pageable);

    /*
         For ordering to work BloodDonor must be first in select because hibernate (we sort name, surname... that are in blood donor table)
         This all args constructor had to be made because also for ordering all fields by which you order must be in select statement
    */
    @Query("select distinct  new groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorInfoDto(bd.id,ac.email,  bd.name, " +
            " bd.surname , bd.phoneNumber, bd.institution, bd.jmbg, bd.gender, bd.occupation, bd.address, bd.points," +
            " bd.penalties)" +
            " from " + " BloodDonor bd " +
            "left join AppointmentSchedulingHistory ash " +
            "on ash.bloodDonor.id = bd.id " +
            "left join Appointment ap " +
            "on ash.appointment.id = ap.id " +
            " left join Account ac " +
            "on bd.id = ac.personId " +
            "where ap.bloodCenter.id = :bcId " +
            "and ash.status = :status " +
            "and upper(bd.name) like CONCAT(upper(:name) ,'%') and" +
            " upper(bd.surname) like CONCAT(upper(:surname) ,'%') ")
    Page<BloodDonorInfoDto> findByNameAndSurnameForCenterAndStatusIgnoreCase(@Param("name") String name, @Param("surname") String surname,
                                                                             @Param("bcId") UUID bloodCenterId, @Param("status") AppointmentSchedulingConfirmationStatus status,
                                                                             Pageable pageable);

    @Modifying
    @Query("update BloodDonor b set b.penalties = 0 ")
    void resetPenalties();

    @Query("select bl from BloodDonor bl inner join fetch bl.appointmentSchedulingHistories a  left join fetch bl.questionnaire q " +
            "where bl.id=:id")
    BloodDonor fetchWithQuestionnaire(@Param("id") UUID id);
}
