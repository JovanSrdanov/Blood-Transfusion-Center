package groupJASS.ISA_2022.DTO.Appointment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class CustomAppointmentTimeWrapper {
    LocalDateTime time;
}
