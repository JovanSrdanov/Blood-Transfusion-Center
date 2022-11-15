package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.BloodDonor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloodDonorRepository extends JpaRepository<BloodDonor, UUID> {
    

    boolean existsBloodUserByJmbg(String jmbg);
}
