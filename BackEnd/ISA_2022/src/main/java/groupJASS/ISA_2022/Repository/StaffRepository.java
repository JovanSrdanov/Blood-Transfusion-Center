package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {
    @Query("select st from Staff st where st.bloodCenter is null")
    Iterable<Staff> getUnemployedStaff();

    @Query("select st from Staff st " +
            "left join Account  ac " +
            "on st.id = ac.personId " +
            "where ac.email = :email")
    Staff findByEmail(@Param("email") String email);
    Optional<Staff> findById(UUID id);
    List<Staff> getByBloodCenterId(UUID bloodCenterId);
}
