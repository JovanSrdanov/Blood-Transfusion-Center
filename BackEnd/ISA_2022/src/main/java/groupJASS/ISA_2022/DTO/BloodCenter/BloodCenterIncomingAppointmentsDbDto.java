package groupJASS.ISA_2022.DTO.BloodCenter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BloodCenterIncomingAppointmentsDbDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String name;
    private String surname;
}
