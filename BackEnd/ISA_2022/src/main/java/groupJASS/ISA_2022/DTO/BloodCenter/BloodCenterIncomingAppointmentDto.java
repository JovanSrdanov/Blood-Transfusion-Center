package groupJASS.ISA_2022.DTO.BloodCenter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@Getter
@Setter
public class BloodCenterIncomingAppointmentDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private String info;

    public BloodCenterIncomingAppointmentDto(LocalDateTime start, LocalDateTime end, String patientName, String patientSurname) {
        this.start = start;
        this.end = end;

        String time = start.format(DateTimeFormatter.ofPattern("HH:mm"));
        String duration = Long.toString(ChronoUnit.MINUTES.between(start, end));

        this.info = time + ", " + duration + "min " + patientName + " " + patientSurname;
    }
}
