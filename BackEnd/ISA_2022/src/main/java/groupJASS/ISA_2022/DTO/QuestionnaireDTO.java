package groupJASS.ISA_2022.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class QuestionnaireDTO {
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
}
