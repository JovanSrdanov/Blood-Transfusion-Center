package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.GPSDemandBloodShipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GPSDemandBloodShipmentRepository extends JpaRepository<GPSDemandBloodShipment, UUID> {
}
