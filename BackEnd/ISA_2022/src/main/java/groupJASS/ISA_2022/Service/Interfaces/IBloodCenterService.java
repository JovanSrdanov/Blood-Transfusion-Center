package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.BloodCenter;

import java.util.List;

public interface IBloodCenterService extends ICrudService<BloodCenter> {
    List<BloodCenter> findProductsWithSorting(String field);
}
