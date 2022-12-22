package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.Equipment;

import java.util.List;
import java.util.UUID;

public interface IEquipmentService extends ICrudService<Equipment>{
    Equipment updateQuantity(UUID equipmentId, double quantity);

    List<Equipment> findByCentre(UUID centreId);
}
