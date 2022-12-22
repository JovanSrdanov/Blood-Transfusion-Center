package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {
    @Query("select st from Staff st where st.bloodCenter is null")
    Iterable<Staff> getUnemployedStaff();

    List<Staff> findAllByBloodCenter(BloodCenter bloodCenter);
}
