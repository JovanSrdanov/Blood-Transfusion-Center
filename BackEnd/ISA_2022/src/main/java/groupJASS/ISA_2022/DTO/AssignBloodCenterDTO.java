package groupJASS.ISA_2022.DTO;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.SecondaryTable;
import java.util.UUID;
@NoArgsConstructor
@Getter
@Setter
public class AssignBloodCenterDTO {
    private UUID bloodAdminId;
    private UUID bloodCenterId;
}
