package groupJASS.ISA_2022.DTO.Staff;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
public class StaffProfileDTO {
    @NotEmpty
    String name;
    @NotEmpty
    String surname;
    @NotEmpty
    String email;
    @NotEmpty
    String phoneNumber;
}
