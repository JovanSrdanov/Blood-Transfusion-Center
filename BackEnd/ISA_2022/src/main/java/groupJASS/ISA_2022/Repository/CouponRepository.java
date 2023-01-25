package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Model.Coupon;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.UUID;

@Repository
@Primary
public interface CouponRepository extends JpaRepository<Coupon, UUID> {
}
