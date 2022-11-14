package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.DTO.BloodAdmin.BloodAdminRegistrationDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.BloodAdmin;

import java.util.UUID;

public interface IBloodAdminService  extends ICrudService<BloodAdmin> {

    public void assignBloodCenter(UUID bloodAdminId, UUID bloodCenterId) throws BadRequestException;
    public Iterable<BloodAdmin> getUnemployedBloodAdmins();
    public void register(BloodAdminRegistrationDTO dto);
}
