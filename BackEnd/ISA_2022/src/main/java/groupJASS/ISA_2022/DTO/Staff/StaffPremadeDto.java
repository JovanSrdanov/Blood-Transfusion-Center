package groupJASS.ISA_2022.DTO.Staff;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class StaffPremadeDto {
    private UUID id;
    private String name;
    private String surname;
}
