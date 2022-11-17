package groupJASS.ISA_2022.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Rating {
    @EmbeddedId
    private CenterDonorRatingCompositeKey id;

    //TODO vidi da li treba lazy
    @ManyToOne
    @MapsId("bloodCenterId")
    private  BloodCenter bloodCenter;
    @ManyToOne
    @MapsId("bloodDonorId")
    private BloodDonor bloodDonor;
    @Column(nullable = false)
    private double rating;

}
