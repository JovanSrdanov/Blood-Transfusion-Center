package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.BloodQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BloodQuantityRepository extends JpaRepository<BloodQuantity, UUID> {
}
