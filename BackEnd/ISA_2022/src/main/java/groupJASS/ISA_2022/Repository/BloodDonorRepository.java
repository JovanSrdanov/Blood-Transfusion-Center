package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.BloodDonor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BloodDonorRepository extends JpaRepository<BloodDonor, UUID> {
    boolean existsBloodUserByJmbg(String jmbg);

    @Query("select bl from BloodDonor bl left join fetch bl.address a  left join fetch bl.questionnaire q")
    List<BloodDonor> findAllWithAddressAndQuestionnaire();
    Iterable<BloodDonor> findBloodDonorByNameAndSurname(String name, String surname);
}
