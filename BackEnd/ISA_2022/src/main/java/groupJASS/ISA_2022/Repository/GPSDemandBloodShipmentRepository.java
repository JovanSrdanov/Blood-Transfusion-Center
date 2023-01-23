package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.GPSDemandBloodShipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface GPSDemandBloodShipmentRepository extends JpaRepository<GPSDemandBloodShipment, UUID> {

    @Query("select gps from GPSDemandBloodShipment gps " +
            "where gps.BloodCenter.id = :bloodCenterId and" +
            " gps.demandBloodShipmentStatus = 0")
    Page<GPSDemandBloodShipment> getAllPendingShipments(@Param("bloodCenterId") UUID bloodCenterId, Pageable pageable);
}
