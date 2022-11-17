package groupJASS.ISA_2022.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class CenterDonorRatingCompositeKey implements Serializable {
    private UUID bloodCenterId;
    private UUID bloodDonorId;
}
