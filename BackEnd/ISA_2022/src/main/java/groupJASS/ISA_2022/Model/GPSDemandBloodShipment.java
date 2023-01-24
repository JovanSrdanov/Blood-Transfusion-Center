package groupJASS.ISA_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GPSDemandBloodShipment {
    @Id
    private UUID id;
    @Column(nullable = false)
    private DemandBloodShipmentStatus demandBloodShipmentStatus;
    @OneToOne
    @JoinColumn(name = "blood_center_id", nullable = false)
    private BloodCenter BloodCenter;
    @Column(nullable = false)
    private String serverPort;
    @Column(nullable = false)
    private float longitude;
    @Column(nullable = false)
    private float latitude;
    @Column(nullable = false)
    private int a_pos;
    @Column(nullable = false)
    private int a_neg;
    @Column(nullable = false)
    private int b_pos;
    @Column(nullable = false)
    private int b_neg;
    @Column(nullable = false)
    private int o_pos;
    @Column(nullable = false)
    private int O_neg;
    @Column(nullable = false)
    private int ab_pos;
    @Column(nullable = false)
    private int ab_neg;


}
