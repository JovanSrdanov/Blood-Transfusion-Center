package groupJASS.ISA_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoyaltyType {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String categoryName;

    @Column(nullable = false)
    private int pointsReq;

    @ManyToMany
    @JoinTable(name="loyalty_coupons",
            joinColumns = @JoinColumn(name = "loyalty_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="coupon_id", referencedColumnName = "id"))
    private Set<Coupon> coupons = new HashSet<>();

}
