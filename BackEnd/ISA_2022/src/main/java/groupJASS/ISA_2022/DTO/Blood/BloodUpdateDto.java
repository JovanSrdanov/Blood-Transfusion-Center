package groupJASS.ISA_2022.DTO.Blood;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class BloodUpdateDto {
    private UUID bloodId;
    private int quantity;
}
