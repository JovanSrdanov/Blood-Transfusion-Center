package ExternalHospital.ExternalHospital.DeliveryContract;

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
    private int a_pos;
    private int a_neg;
    private int b_pos;
    private int b_neg;
    private int o_pos;
    private int O_neg;
    private int ab_pos;
    private int ab_neg;
    private Date delivery;
}
