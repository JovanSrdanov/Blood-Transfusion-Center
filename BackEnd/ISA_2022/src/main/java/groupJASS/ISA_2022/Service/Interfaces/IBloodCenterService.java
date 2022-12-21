package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Model.BloodQuantity;
import groupJASS.ISA_2022.Model.DateRange;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IBloodCenterService extends ICrudService<BloodCenter> {
    Page<BloodCenter> findProductsWithSorting(int offset, int pageSize, String field, String sort, String s) throws SortNotFoundException;

    DateRange getWorkingDateRangeForDate(UUID id, LocalDateTime date);
}
