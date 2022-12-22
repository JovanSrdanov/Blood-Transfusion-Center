package groupJASS.ISA_2022.DTO.AppointmentReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class AppointmentReportCreate {
    @NotEmpty
    private UUID appointmentHistoryId;
    @NotEmpty
    private UUID equipmentId;
    @NotEmpty
    private String text;
}
