package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.BloodAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BloodAdminRepository extends JpaRepository<BloodAdmin, UUID> {
    @Query("select ba from BloodAdmin ba where ba.bloodCenter is null")
    public Iterable<BloodAdmin> getUnemployedBloodAdmins();
}
