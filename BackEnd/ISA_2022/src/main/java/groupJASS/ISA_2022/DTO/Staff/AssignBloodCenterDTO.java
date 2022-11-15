package groupJASS.ISA_2022.DTO.Staff;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class AssignBloodCenterDTO {
    private UUID staffId;
    private UUID bloodCenterId;
}
