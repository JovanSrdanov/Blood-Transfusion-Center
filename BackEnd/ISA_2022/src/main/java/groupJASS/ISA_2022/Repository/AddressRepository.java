package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.Address;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Primary
public interface AddressRepository extends JpaRepository<Address, UUID> {

}
