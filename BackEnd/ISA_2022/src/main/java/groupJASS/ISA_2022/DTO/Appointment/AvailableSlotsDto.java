package groupJASS.ISA_2022.DTO.Appointment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AvailableSlotsDto {
    @NotEmpty
    private List<String> staffIds;

    @NotEmpty
    private LocalDateTime date;

    @NotEmpty
    private int duration;
}
