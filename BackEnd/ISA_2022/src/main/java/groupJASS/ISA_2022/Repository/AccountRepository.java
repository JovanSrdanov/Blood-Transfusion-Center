package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Boolean existsAccountByEmail(String email);
}
