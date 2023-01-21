package groupJASS.ISA_2022.DTO.Appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppointmentCancelation {
    UUID appointmentHistoryId;
    //UUID bloodDonorId;
    boolean showedUp;
}
