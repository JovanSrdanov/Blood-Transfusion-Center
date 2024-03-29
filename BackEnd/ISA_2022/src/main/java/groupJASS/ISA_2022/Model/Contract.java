package groupJASS.ISA_2022.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Contract {
    @Id
    private UUID id;

    @Column(nullable = false)
    private BloodGroup bloodGroup;

    @Column(nullable = false)
    private double bloodQuantity;

    @Column(nullable = false)
    private LocalDate deliveryDate;
}
