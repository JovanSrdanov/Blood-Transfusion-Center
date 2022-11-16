package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.BloodCenter;
import org.springframework.data.domain.Page;

public interface IBloodCenterService extends ICrudService<BloodCenter> {
    Page<BloodCenter> findProductsWithSorting(int offset, int pageSize, String field, String sort, String s) throws SortNotFoundException;
}
