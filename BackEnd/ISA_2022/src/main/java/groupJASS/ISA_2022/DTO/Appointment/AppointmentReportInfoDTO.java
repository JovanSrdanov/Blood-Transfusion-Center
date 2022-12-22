package groupJASS.ISA_2022.DTO.Appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppointmentReportInfoDTO {

    private UUID AppointmentReportId;
    private LocalDateTime dateTimeStart;
    private int duration;
    private String text;
}
