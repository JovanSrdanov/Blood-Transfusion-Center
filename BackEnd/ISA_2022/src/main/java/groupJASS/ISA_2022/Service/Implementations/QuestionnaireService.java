package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Questionnaire;
import groupJASS.ISA_2022.Repository.QuestionnaireRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodDonorService;
import groupJASS.ISA_2022.Service.Interfaces.IQuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
@Primary
public class QuestionnaireService implements IQuestionnaireService {

    private final QuestionnaireRepository _questionnaireRepository;
    private final IBloodDonorService _bloodDonorService;

    @Autowired
    public QuestionnaireService(QuestionnaireRepository questionnaireRepository, IBloodDonorService bloodDonorService) {
        this._questionnaireRepository = questionnaireRepository;
        _bloodDonorService = bloodDonorService;

    }

    @Override
    public Iterable<Questionnaire> findAll() {
        return _questionnaireRepository.findAll();
    }

    @Override
    public Questionnaire findById(UUID id) {
        if (_questionnaireRepository.findById(id).isPresent()) {

            return _questionnaireRepository.findById(id).get();
        }

        throw new NotFoundException("Questionnaire not found");
    }

    @Override
    public Questionnaire save(Questionnaire entity) throws BadRequestException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        return _questionnaireRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _questionnaireRepository.deleteById(id);
    }

    @Override
    public Boolean canDonateBlood(UUID bloodDonorId) {
        Questionnaire questionare = _bloodDonorService.getQuestionnaireFromBloodDonor(bloodDonorId);
        return questionare.canDonateBlood();


    }

    @Override
    public void fillQuestionnaire(Questionnaire map, UUID bloodDonorId) throws BadRequestException {
        var Questionnaire = save(map);
        var regUser = _bloodDonorService.findById(bloodDonorId);
        regUser.setQuestionnaire(Questionnaire);
        _bloodDonorService.save(regUser);

    }

}
