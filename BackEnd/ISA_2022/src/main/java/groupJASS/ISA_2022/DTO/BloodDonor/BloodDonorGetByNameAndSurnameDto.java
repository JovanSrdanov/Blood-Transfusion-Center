package groupJASS.ISA_2022.DTO.BloodDonor;

import groupJASS.ISA_2022.Utilities.SortType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BloodDonorGetByNameAndSurnameDto {
    private String name;
    private String surname;
    int page;
    int pageSize;
    String sortByField;
    SortType sortType;
}
