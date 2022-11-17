package groupJASS.ISA_2022.DTO.BloodCenter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class WorkingHoursDTO {
    @NotNull
    private int startHours;
    @NotNull
    private int startMinutes;
    @NotNull
    private int endHours;
    @NotNull
    private int endMinutes;
}
