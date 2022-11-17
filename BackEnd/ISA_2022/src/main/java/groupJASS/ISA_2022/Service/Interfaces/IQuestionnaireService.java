package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Questionnaire;

import java.util.UUID;

public interface IQuestionnaireService extends ICrudService<Questionnaire> {
    Boolean canDonateBlood(UUID bloodDonorId);

    void fillQuestionare(Questionnaire map, UUID bloodDonorId) throws BadRequestException;
}
