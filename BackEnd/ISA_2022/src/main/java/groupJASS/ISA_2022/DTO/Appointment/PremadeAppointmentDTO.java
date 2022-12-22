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
public class PremadeAppointmentDTO {
    //Jovan pravio, Strahinja ne diraj
    //Fuj to
    private UUID appointmentId;
    private LocalDateTime dateTimeStart;
    private int duration;
}
