package groupJASS.ISA_2022.DTO.Address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


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
    private double latitude;
    private double longitude;
}