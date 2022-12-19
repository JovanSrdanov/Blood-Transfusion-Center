package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorInfoDto;
import groupJASS.ISA_2022.DTO.BloodDonor.RegisterBloodDonorDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Model.Questionnaire;

import java.util.List;
import java.util.UUID;

public interface IBloodDonorService extends ICrudService<BloodDonor> {
    BloodDonor RegisterUser(BloodDonor map, Address address);

    Questionnaire getQuestionnaireFromBloodDonor(UUID bloodDonorId);

    List<BloodDonor> findAllWithAddressAndQuestionnaire();

    void registerNewBloodDonor(RegisterBloodDonorDTO dto);

    void sendActvivationToken(RegisterBloodDonorDTO dto);

    BloodDonor updateDonorInfo(Address map, BloodDonor updatedUser) throws BadRequestException;

    List<BloodDonorInfoDto> findBloodDonorByNameAAndSurname(String name, String surname);
}
