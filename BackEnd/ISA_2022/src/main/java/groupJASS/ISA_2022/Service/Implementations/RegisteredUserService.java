package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.Questionnaire;
import groupJASS.ISA_2022.Model.RegisteredUser;
import groupJASS.ISA_2022.Repository.QuestionnaireRepository;
import groupJASS.ISA_2022.Repository.RegisteredUserRepository;
import groupJASS.ISA_2022.Service.Interfaces.IRegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@Primary
public class RegisteredUserService implements IRegisteredUserService {

    private final RegisteredUserRepository _registeredUserRepository;
    private final QuestionnaireRepository _questionnaireRepository;

    @Autowired
    public RegisteredUserService(RegisteredUserRepository registeredUserRepository, QuestionnaireRepository questionnaireRepository) {
        _registeredUserRepository = registeredUserRepository;
        _questionnaireRepository = questionnaireRepository;
    }

    @Override
    public List<RegisteredUser> findAll() {
        return _registeredUserRepository.findAll();
    }

    @Override
    public RegisteredUser findById(UUID id) {
        if (_registeredUserRepository.findById(id).isPresent()) {

            return _registeredUserRepository.findById(id).get();
        }

        throw new NotFoundException("Registered user not found");
    }

    @Override
    public RegisteredUser save(RegisteredUser entity) {
        RegisteredUser user;
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
            user = _registeredUserRepository.save(entity);
        }
        else {
            RegisteredUser oldUser = findById(entity.getId());
            if(oldUser == null) {
                throw new NotFoundException("Registered user not found");
            }
            oldUser.update(entity);
            user = _registeredUserRepository.save(oldUser);
        }

        return user;
    }

    @Override
    public void deleteById(UUID id) {
        _registeredUserRepository.deleteById(id);
    }

    @Override
    public RegisteredUser RegisterUser(RegisteredUser map, Address address) {
        if (_registeredUserRepository.existsBloodUserByJmbg(map.getJmbg())) {

            throw new IllegalArgumentException("RegisterUser with this jmbg already exists");
        }
        if (_registeredUserRepository.existsBloodUserByEmail(map.getEmail())) {

            throw new IllegalArgumentException("RegisterUser with this email already exists");
        }
        map.setAddress(address);
        map.setActivated(false);
        map.setPoints(0);
        return save(map);
    }

    @Override
    public Questionnaire getQuestionnaireFromBloodDonor(UUID bloodDonorId) {
        var BloodDonor = findById(bloodDonorId);

        if (BloodDonor.getQuestionnaire() != null) {

            return BloodDonor.getQuestionnaire();
        }

        throw new NotFoundException("Blood donor does not have Questionnaire");

    }
}
