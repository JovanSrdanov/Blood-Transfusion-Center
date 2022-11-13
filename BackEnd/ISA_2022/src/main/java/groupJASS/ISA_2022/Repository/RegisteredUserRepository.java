package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, UUID> {
    boolean existsBloodUserByEmail(String email);

    boolean existsBloodUserByJmbg(String jmbg);
}
