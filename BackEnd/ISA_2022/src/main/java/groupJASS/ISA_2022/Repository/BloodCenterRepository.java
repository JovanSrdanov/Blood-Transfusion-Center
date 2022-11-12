package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.BloodCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloodCenterRepository extends JpaRepository<BloodCenter, UUID> {
}
