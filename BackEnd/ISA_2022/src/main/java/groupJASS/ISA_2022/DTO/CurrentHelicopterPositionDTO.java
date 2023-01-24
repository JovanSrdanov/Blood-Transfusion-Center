package groupJASS.ISA_2022.DTO;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CurrentHelicopterPositionDTO {
    private double srcLatitude;
    private double destLongitude;
    private double destLatitude;
    private UUID shipmentID;
    private int seconds;
    private double curLongitude;
    private double curLatitude;
}
