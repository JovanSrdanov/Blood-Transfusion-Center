package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.BloodDonor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BloodDonorRepository extends JpaRepository<BloodDonor, UUID> {
    boolean existsBloodUserByJmbg(String jmbg);

    @Query("select bl from BloodDonor bl left join fetch bl.address a  left join fetch bl.questionnaire q")
    List<BloodDonor> findAllWithAddressAndQuestionnaire();

    Iterable<BloodDonor> findBloodDonorByNameAndSurname(String name, String surname);

    @Query("select  bd from BloodDonor bd" +
            " where upper(bd.name) like CONCAT(upper(coalesce(:name,'%') ) ,'%') and" +
            "  upper(bd.surname) like CONCAT(upper(coalesce(:surname, '%') ) ,'%') ")
    Iterable<BloodDonor> searchByNameAndSurnameIgnoreCase(String name, String surname);

    @Modifying
    @Query("update BloodDonor b set b.penalties = 0 ")
    void resetPenalties();

    @Query("select bl from BloodDonor bl inner join fetch bl.appointmentSchedulingHistories a  left join fetch bl.questionnaire q " +
            "where bl.id=:id")
    BloodDonor fetchWithQuestionnaire(@Param("id") UUID id);


}
