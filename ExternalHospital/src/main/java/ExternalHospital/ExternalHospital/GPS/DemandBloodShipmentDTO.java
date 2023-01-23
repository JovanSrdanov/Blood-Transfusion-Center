package ExternalHospital.ExternalHospital.GPS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DemandBloodShipmentDTO implements Serializable {
    private UUID bloodCenterId;
    private String serverPort;
    private float longitude;
    private float latitude;
    private int A_POS;
    private int A_NEG;
    private int B_POS;
    private int B_NEG;
    private int O_POS;
    private int O_NEG;
    private int AB_POS;
    private int AB_NEG;

    @Override
    public String toString() {
        return "DemandBloodShipmentDTO{" +
                "bloodCenterId=" + bloodCenterId +
                ", localHost='" + serverPort + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", A_POS=" + A_POS +
                ", A_NEG=" + A_NEG +
                ", B_POS=" + B_POS +
                ", B_NEG=" + B_NEG +
                ", O_POS=" + O_POS +
                ", O_NEG=" + O_NEG +
                ", AB_POS=" + AB_POS +
                ", AB_NEG=" + AB_NEG +
                '}';
    }
}
