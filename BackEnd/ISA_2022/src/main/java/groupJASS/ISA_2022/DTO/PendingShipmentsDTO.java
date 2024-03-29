package groupJASS.ISA_2022.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PendingShipmentsDTO {

    private UUID id;
    private String serverPort;
    private double longitude;
    private double latitude;
    private int a_pos;
    private int a_neg;
    private int b_pos;
    private int b_neg;
    private int o_pos;
    private int O_neg;
    private int ab_pos;
    private int ab_neg;
}
