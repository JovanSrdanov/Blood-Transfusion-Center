package groupJASS.ISA_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Questionnaire {
    @Id
    private UUID id;
    @NotNull
    private boolean under50KG;
    @NotNull
    private boolean feelsBad;
    @NotNull
    private boolean skinChanges;
    @NotNull
    private boolean highOrLowPresure;
    @NotNull
    private boolean onTherapy;
    @NotNull
    private boolean onMC;
    @NotNull
    private boolean recentDentist;
    @NotNull
    private boolean pricingOrSurgicalInterventionsOrBloodTransfusion;

    public boolean canDonateBlood() {
        return (!under50KG && !feelsBad && !skinChanges && !highOrLowPresure && !onTherapy && !onMC && !recentDentist && !pricingOrSurgicalInterventionsOrBloodTransfusion);
    }


}
