package ExternalHospital.ExternalHospital.DeliveryContract;

import ExternalHospital.ExternalHospital.Model.BloodGroup;
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
    private BloodGroup bloodGroup;
    private int quantity;
    private int deliveryDay;
    private String queueName;
}
