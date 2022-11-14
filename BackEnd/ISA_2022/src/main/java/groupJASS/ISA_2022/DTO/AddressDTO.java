package groupJASS.ISA_2022.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class AddressDTO {
    @NotEmpty
    private String street;
    @NotEmpty
    private String number;
    @NotEmpty
    private String city;
    @NotEmpty
    private String country;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
}