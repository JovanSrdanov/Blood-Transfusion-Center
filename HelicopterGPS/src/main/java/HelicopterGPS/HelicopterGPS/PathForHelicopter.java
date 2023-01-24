package HelicopterGPS.HelicopterGPS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PathForHelicopter {
    @Id
    private UUID id;

    @Column(nullable = false)
    private double srcLongitude;
    @Column(nullable = false)
    private double srcLatitude;
    @Column(nullable = false)
    private double destLongitude;
    @Column(nullable = false)
    private double destLatitude;
    @Column(nullable = false)
    private UUID shipmentID;
    @Column(nullable = false)
    private int seconds;

    @Column(nullable = false)
    private double  curLongitude;
    @Column(nullable = false)
    private double curLatitude;
    boolean isDelivered;

}
