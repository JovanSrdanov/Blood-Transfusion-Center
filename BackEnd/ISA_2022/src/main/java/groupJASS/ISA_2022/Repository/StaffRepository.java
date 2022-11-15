package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {
    @Query("select ba from Staff ba where ba.bloodCenter is null")
    Iterable<Staff> getUnemployedBloodAdmins();

    boolean existsBloodAdminByEmail(String email);
}
