package groupJASS.ISA_2022.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class AddressDTO {
    private String street;
    private String number;
    private String city;
    private String country;
    private double latitude;
    private double longitude;
}