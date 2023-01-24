package HelicopterGPS.HelicopterGPS;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CoordinatesForGPSDTO {

    private double srcLongitude;
    private double srcLatitude;
    private double destLongitude;
    private double destLatitude;
    private UUID shipmentID;
    private int seconds;

}
