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
public class CoordinatesForGPSDTO {

    private double srcLongitude;
    private double srcLatitude;
    private double destLongitude;
    private double destLatitude;
    private UUID shipmentID;

    private int seconds;

}
