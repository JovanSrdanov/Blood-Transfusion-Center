package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.BloodAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloodAdminRepository extends JpaRepository<BloodAdmin, UUID> {
}
