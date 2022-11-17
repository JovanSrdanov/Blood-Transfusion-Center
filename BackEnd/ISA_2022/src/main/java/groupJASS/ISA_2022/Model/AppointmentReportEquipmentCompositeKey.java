package groupJASS.ISA_2022.Model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class AppointmentReportEquipmentCompositeKey implements Serializable {
    private UUID reportId;
    private UUID equipmentId;
}
