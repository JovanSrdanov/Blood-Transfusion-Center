package groupJASS.ISA_2022.DTO.BloodCenter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkingHoursRoundedDto {
    @NotNull
    private int startHours;
    @NotNull
    private int endHours;
}
