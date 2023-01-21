package groupJASS.ISA_2022.DTO.Appointment;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppointmentQrInformationDto {
    public UUID appointmentId;
    public UUID bloodDonorId;
}
