package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.Account;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Boolean existsAccountByEmail(String email);

    Account findAccountByPersonId(UUID personId);

    Account findByEmail(String email);
    @Transactional //Required by JPA
    @Modifying
    @Query( "Update Account  " +
            "set password  = :newPassword , lastPasswordUpdateDate = :lastPasswordUpdateDate" +
            " where email = :email")
    public void updatePassword(String email, String newPassword, Timestamp lastPasswordUpdateDate);
}
