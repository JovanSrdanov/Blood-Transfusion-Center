package HelicopterGPS.HelicopterGPS;

import lombok.*;

import javax.persistence.Column;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CurrentHelicopterPositionDTO {
    private double srcLatitude;
    private double srcLongitude;
    private double destLongitude;
    private double destLatitude;
    private UUID shipmentID;
    private int seconds;
    private double  curLongitude;
    private double curLatitude;
}
