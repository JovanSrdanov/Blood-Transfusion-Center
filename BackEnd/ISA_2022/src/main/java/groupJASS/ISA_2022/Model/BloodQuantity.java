package groupJASS.ISA_2022.Model;

import groupJASS.ISA_2022.Repository.BloodQuantityRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class BloodQuantity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private BloodGroup bloodGroup;

    @Column(nullable = false)
    //In liters
    private int quantity;

    public BloodQuantity(BloodGroup bloodGroup)
    {
        this.id = UUID.randomUUID();
        this.quantity = 0;
        this.bloodGroup = bloodGroup;
    }
}
