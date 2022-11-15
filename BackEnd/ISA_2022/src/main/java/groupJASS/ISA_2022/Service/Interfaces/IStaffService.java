package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.DTO.Staff.StaffRegistrationDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Staff;

import java.util.UUID;

public interface IStaffService extends ICrudService<Staff> {

    void assignBloodCenter(UUID bloodAdminId, UUID bloodCenterId) throws BadRequestException;

    Iterable<Staff> getUnemployedBloodAdmins();

    void register(StaffRegistrationDTO dto);

    boolean checkEmailAvailability(String email);
}
