package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.BloodAdmin;

import java.util.UUID;

public interface IBloodAdminService  extends ICrudService<BloodAdmin> {

    public void assignBloodCenter(UUID bloodAdminId, UUID bloodCenterId);
}
