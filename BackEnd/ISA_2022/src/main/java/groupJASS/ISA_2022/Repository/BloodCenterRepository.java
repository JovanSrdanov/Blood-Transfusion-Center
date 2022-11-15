package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.BloodCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BloodCenterRepository extends JpaRepository<BloodCenter, UUID> {

    @Query("select bc from BloodCenter bc inner join Address a on bc.address.id = a.id " +
            "where bc.name like CONCAT('%',:s,'%') or a.city like CONCAT('%',:s,'%')")
    Page<BloodCenter> searchBy(@Param("s") String s, Pageable pageable);
}
