package groupJASS.ISA_2022.DTO.BloodDonor;

import groupJASS.ISA_2022.Utilities.SortType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BloodDonorGetByNameAndSurnameDto {
    //Search
    public String name;
    public String surname;
    //Paging and sorting
    public int page;
    public int pageSize;
    public  String sortByField;
    public SortType sortType;
}
