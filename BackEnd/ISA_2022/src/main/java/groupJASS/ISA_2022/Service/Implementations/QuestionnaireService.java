package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Questionnaire;
import groupJASS.ISA_2022.Model.RegisteredUser;
import groupJASS.ISA_2022.Repository.QuestionnaireRepository;
import groupJASS.ISA_2022.Service.Interfaces.IQuestionnaireService;
import groupJASS.ISA_2022.Service.Interfaces.IRegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@Primary
public class QuestionnaireService implements IQuestionnaireService {

    private final QuestionnaireRepository _questionnaireRepository;
    private final IRegisteredUserService _registeredUserService;

    @Autowired
    public QuestionnaireService(QuestionnaireRepository questionnaireRepository, IRegisteredUserService registeredUserService) {
        this._questionnaireRepository = questionnaireRepository;
        _registeredUserService = registeredUserService;

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
        var questionare = _registeredUserService.getQuestionnaireFromBloodDonor(bloodDonorId);
        return questionare.canDonateBlood();


    }

    @Override
    public void fillQuestionare(Questionnaire map, UUID bloodDonorId) throws BadRequestException {
        //Todo: promeni samo ovo i uradi proveru
        var Questionnaire = save(map);
        var regUser = ((List<RegisteredUser>) _registeredUserService.findAll()).get(0);
        regUser.setQuestionnaire(Questionnaire);
        _registeredUserService.save(regUser);

    }

}
