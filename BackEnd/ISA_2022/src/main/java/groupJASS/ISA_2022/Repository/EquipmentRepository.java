package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {
    @Query("select eq from Equipment eq " +
    "left outer join fetch BloodCenter bc on eq.bloodCenter.id = bc.id " +
    "where eq.bloodCenter.id = :bloodCenterId")
    List<Equipment> findByCentre(@Param("bloodCenterId") UUID bloodCenterId);
}
