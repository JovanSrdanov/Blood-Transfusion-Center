package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.BloodUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloodUserRepository extends JpaRepository<BloodUser, UUID> {

}
