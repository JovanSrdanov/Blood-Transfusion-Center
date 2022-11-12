package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.LoyaltyType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Primary
public interface LoyaltyRepository extends JpaRepository<LoyaltyType, UUID> {

}
