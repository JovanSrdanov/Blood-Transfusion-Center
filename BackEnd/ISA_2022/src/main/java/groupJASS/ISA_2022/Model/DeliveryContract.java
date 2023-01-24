package groupJASS.ISA_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryContract {
    @Id
    @Column(nullable = false)
    private UUID id;
    @Column(nullable = false)
    private BloodGroup bloodGroup;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private int deliveryDay;
}
