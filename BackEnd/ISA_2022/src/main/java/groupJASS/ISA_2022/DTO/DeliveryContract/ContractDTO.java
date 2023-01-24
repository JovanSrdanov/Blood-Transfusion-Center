package groupJASS.ISA_2022.DTO.DeliveryContract;

import groupJASS.ISA_2022.Model.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractDTO {
    private UUID id;
    BloodGroup bloodGroup;
    private int quantity;
    private int deliveryDay;
}
