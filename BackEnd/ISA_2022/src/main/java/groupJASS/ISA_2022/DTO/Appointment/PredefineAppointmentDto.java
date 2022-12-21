package groupJASS.ISA_2022.DTO.Appointment;

import groupJASS.ISA_2022.Model.DateRange;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class PredefineAppointmentDto {
    DateRange dateRange;
    List<UUID> staffIds;
}
