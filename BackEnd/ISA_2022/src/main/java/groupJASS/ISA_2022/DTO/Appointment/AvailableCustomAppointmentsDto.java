package groupJASS.ISA_2022.DTO.Appointment;

import groupJASS.ISA_2022.DTO.BloodCenter.BloodCenterBasicInfoDto;
import groupJASS.ISA_2022.Model.DateRange;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class AvailableCustomAppointmentsDto {
    @NotEmpty
    DateRange time;

    @Valid
    BloodCenterBasicInfoDto centerInfo;

    @NotEmpty
    UUID staffId;

    public AvailableCustomAppointmentsDto(DateRange time, BloodCenterBasicInfoDto bloodCenterBasicInfoDto, UUID staffId) {
        this.time = time;
        this.centerInfo = bloodCenterBasicInfoDto;
        this.staffId = staffId;
    }

}
