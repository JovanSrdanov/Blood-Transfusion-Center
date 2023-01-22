package groupJASS.ISA_2022.DTO.Staff;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
public class StaffFullNameDto {
    @NotEmpty
    public String name;
    @NotEmpty
    public String surname;

    public StaffFullNameDto(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
