package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorGetByNameAndSurnameDto;
import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorInfoDto;
import groupJASS.ISA_2022.DTO.BloodDonor.RegisterBloodDonorDTO;
import groupJASS.ISA_2022.DTO.PageEntityDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.*;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Model.Questionnaire;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IBloodDonorService extends ICrudService<BloodDonor> {
    BloodDonor RegisterBloodDonor(BloodDonor map, Address address);

    Questionnaire getQuestionnaireFromBloodDonor(UUID bloodDonorId);

    List<BloodDonor> findAllWithAddressAndQuestionnaire();

    Account registerNewBloodDonor(RegisterBloodDonorDTO dto) throws BadRequestException;

    BloodDonor updateDonorInfo(Address map, BloodDonor updatedUser) throws BadRequestException;

    PageEntityDto<List<BloodDonorInfoDto>> findBloodDonorByNameAndSurname(BloodDonorGetByNameAndSurnameDto dto);
    PageEntityDto<List<BloodDonorInfoDto>> findBloodDonorByNameAndSurnameForCenterAndStatus(BloodDonorGetByNameAndSurnameDto dto,
                                                                                            AppointmentSchedulingConfirmationStatus status,
                                                                                            Principal principal);

    void updatePenalties(BloodDonor donor, boolean showedUp);


    void sendActvivationToken(ActivateAccount activateAccount);
}
