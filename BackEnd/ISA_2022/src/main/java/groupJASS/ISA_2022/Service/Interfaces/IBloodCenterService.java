package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Model.BloodQuantity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBloodCenterService extends ICrudService<BloodCenter> {
    Page<BloodCenter> findProductsWithSorting(int offset, int pageSize, String field, String s);
}
