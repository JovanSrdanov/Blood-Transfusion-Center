package groupJASS.ISA_2022.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DemandBloodShipmentDTO {
    @NotEmpty
    private UUID bloodCenterId;
    @NotEmpty
    private String serverPort;
    @NotEmpty
    private double longitude;
    @NotEmpty
    private double latitude;
    private int a_pos;
    private int a_neg;
    private int b_pos;
    private int b_neg;
    private int o_pos;
    private int O_neg;
    private int ab_pos;
    private int ab_neg;

    @Override
    public String toString() {
        return "DemandBloodShipmentDTO{" +
                "bloodCenterId=" + bloodCenterId +
                ", serverPort='" + serverPort + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", a_pos=" + a_pos +
                ", a_neg=" + a_neg +
                ", b_pos=" + b_pos +
                ", b_neg=" + b_neg +
                ", o_pos=" + o_pos +
                ", O_neg=" + O_neg +
                ", ab_pos=" + ab_pos +
                ", ab_neg=" + ab_neg +
                '}';
    }
}
