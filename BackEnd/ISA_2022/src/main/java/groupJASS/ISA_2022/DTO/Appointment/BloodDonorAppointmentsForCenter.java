package groupJASS.ISA_2022.DTO.Appointment;

import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class BloodDonorAppointmentsForCenter {
    private UUID id;
    private LocalDateTime issuingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public BloodDonorAppointmentsForCenter(AppointmentSchedulingHistory history) {
        this.id = history.getId();
        this.issuingDate = history.getIssuingDate();
        this.startTime = history.getAppointment().getTime().getStartTime();
        this.endTime = history.getAppointment().getTime().getEndTime();
    }
}
