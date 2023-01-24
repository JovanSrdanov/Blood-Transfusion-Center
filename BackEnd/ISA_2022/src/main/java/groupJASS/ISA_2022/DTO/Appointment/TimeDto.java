package groupJASS.ISA_2022.DTO.Appointment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class TimeDto {
    @NotEmpty
    private LocalDateTime time;

    @NotEmpty
    private UUID staffId;
}
