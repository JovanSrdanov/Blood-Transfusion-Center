package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.DeliveryContract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryContractRepository extends JpaRepository<DeliveryContract, UUID>{
}
