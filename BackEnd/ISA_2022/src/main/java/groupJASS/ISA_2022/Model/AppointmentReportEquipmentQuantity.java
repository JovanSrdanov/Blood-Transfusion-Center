package groupJASS.ISA_2022.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class AppointmentReportEquipmentQuantity {
    @EmbeddedId
    private AppointmentReportEquipmentCompositeKey id;
    //TODO vidi da li treba lazy
    @ManyToOne
    @MapsId("reportId")
    private AppointmentReport report;
    @ManyToOne
    @MapsId("equipmentId")
    private Equipment equipment;


    @Column(nullable = false)
    private double usedQuantity;
}
