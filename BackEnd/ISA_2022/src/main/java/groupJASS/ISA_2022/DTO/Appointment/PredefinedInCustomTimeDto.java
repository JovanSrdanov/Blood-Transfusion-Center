package groupJASS.ISA_2022.DTO.Appointment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class PredefinedInCustomTimeDto
{
    @Valid
    private AvailableCustomAppointmentsDto info;

    @NotEmpty
    private UUID Id;
}
