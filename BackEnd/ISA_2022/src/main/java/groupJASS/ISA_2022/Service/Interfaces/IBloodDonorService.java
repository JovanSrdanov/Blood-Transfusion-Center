package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Model.Questionnaire;

import java.util.UUID;

public interface IBloodDonorService extends ICrudService<BloodDonor> {
    BloodDonor RegisterUser(BloodDonor map, Address address);

    Questionnaire getQuestionnaireFromBloodDonor(UUID bloodDonorId);
}