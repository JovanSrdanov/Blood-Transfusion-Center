package groupJASS.ISA_2022.DTO.Appointment;

import groupJASS.ISA_2022.Model.DateRange;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class AvailablePredefinedDto {
    @NotEmpty
    public DateRange time;

    @NotEmpty
    public UUID id;
}
