package groupJASS.ISA_2022.DTO.BloodCenter;

import groupJASS.ISA_2022.DTO.Address.AddressDTO;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.Appointment;
import groupJASS.ISA_2022.Model.BloodQuantity;
import groupJASS.ISA_2022.Model.Staff;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class BloodCenterProfileDto {
    private UUID id;
    @NotEmpty
    private String name;
    @NotEmpty
    private  String description;
    @Valid
    private AddressDTO address;
    private double rating;
    //Stefan dodao
    @NotEmpty
    private Set<BloodQuantity> bloodQuantities;
    private Set<Appointment> appointments;
    private Set<Staff> staff;
}
