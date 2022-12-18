package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByName(String name);
}
