package groupJASS.ISA_2022.DTO.Appointment;

import groupJASS.ISA_2022.Model.AppointmentSchedulingConfirmationStatus;
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
public class QrCodeASHDTO {
    private UUID ASHid;
    private String QRCodeMessage;
    private AppointmentSchedulingConfirmationStatus status;
    private LocalDateTime issuingDate;
}
