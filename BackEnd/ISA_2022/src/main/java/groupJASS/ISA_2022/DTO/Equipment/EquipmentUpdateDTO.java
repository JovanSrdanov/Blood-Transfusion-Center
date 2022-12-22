package groupJASS.ISA_2022.DTO.Equipment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class EquipmentUpdateDTO {
    @NotEmpty
    private UUID equipmentId;
    @NotEmpty
    private double quantity;
}
