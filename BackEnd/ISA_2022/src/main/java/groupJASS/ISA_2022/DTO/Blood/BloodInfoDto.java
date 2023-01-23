package groupJASS.ISA_2022.DTO.Blood;

import groupJASS.ISA_2022.Model.BloodGroup;
import groupJASS.ISA_2022.Model.BloodQuantity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class BloodInfoDto {
    @NotEmpty
    private UUID bloodId;
    @NotEmpty
    private BloodGroup bloodGroup;
    @NotEmpty
    private int quantity;

    public BloodInfoDto(BloodQuantity quantity) {
        this.bloodId = quantity.getId();
        this.bloodGroup = quantity.getBloodGroup();
        this.quantity = quantity.getQuantity();
    }
}
