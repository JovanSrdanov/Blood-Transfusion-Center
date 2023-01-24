package groupJASS.ISA_2022.DTO.Staff;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class StaffFreeSlotsInfo {
    private UUID id;
    @NotEmpty
    String name;
    @NotEmpty
    String surname;
}
