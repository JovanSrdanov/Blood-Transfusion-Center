package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.DTO.Account.PasswordDTO;
import groupJASS.ISA_2022.DTO.Staff.StaffBasicInfoDTO;
import groupJASS.ISA_2022.DTO.Staff.StaffFreeSlotsInfo;
import groupJASS.ISA_2022.DTO.Staff.StaffProfileDTO;
import groupJASS.ISA_2022.DTO.Staff.StaffRegistrationDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Staff;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IStaffService extends ICrudService<Staff> {

    Staff findByEmail(Principal principal) throws NotFoundException;

    void assignBloodCenter(UUID bloodAdminId, UUID bloodCenterId) throws BadRequestException;

    List<StaffBasicInfoDTO> getUnemployedStaff();
    List<StaffProfileDTO> getAllStaff();

    void changePassword(UUID id, PasswordDTO dto);
    void register(StaffRegistrationDTO dto);

    List<StaffFreeSlotsInfo> getByBloodCenterId(UUID bloodCenterId);
}
