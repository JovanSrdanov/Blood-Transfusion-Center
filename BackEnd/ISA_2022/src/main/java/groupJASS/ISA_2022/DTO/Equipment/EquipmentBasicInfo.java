package groupJASS.ISA_2022.DTO.Equipment;

import groupJASS.ISA_2022.Model.Equipment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class EquipmentBasicInfo {
    private UUID equipmentId;
    private String name;
    private double quantity;

    public EquipmentBasicInfo(Equipment equipment) {
        this.equipmentId = equipment.getId();
        this.name = equipment.getName();
        this.quantity = equipment.getQuantity();
    }
}

