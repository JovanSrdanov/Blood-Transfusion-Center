package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.DTO.BloodDonor.RegisterBloodDonorDTO;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Model.Questionnaire;
import groupJASS.ISA_2022.Repository.BloodDonorRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAddressService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodDonorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@Primary
public class BloodDonorService implements IBloodDonorService {

    private final BloodDonorRepository _bloodDonorRepository;
    private final IAddressService _addressService;
    private final IAccountService _accountService;
    private final ModelMapper _mapper;

    @Autowired
    public BloodDonorService(BloodDonorRepository bloodDonorRepository, IAddressService addressService, IAccountService accountService, ModelMapper mapper) {
        _bloodDonorRepository = bloodDonorRepository;
        _addressService = addressService;
        _accountService = accountService;
        _mapper = mapper;
    }

    @Override
    public List<BloodDonor> findAll() {
        return _bloodDonorRepository.findAll();
    }

    @Override
    public BloodDonor findById(UUID id) {
        if (_bloodDonorRepository.findById(id).isPresent()) {

            return _bloodDonorRepository.findById(id).get();
        }

        throw new NotFoundException("Blood donornot found");
    }

    @Override
    public BloodDonor save(BloodDonor entity) {
        BloodDonor user;
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
            user = _bloodDonorRepository.save(entity);
        } else {
            BloodDonor oldUser = findById(entity.getId());
            if (oldUser == null) {
                throw new NotFoundException("Blood donor not found");
            }
            oldUser.update(entity);
            user = _bloodDonorRepository.save(oldUser);
        }

        return user;
    }

    @Override
    public void deleteById(UUID id) {
        _bloodDonorRepository.deleteById(id);
    }

    @Override
    public BloodDonor RegisterUser(BloodDonor map, Address address) {


        if (_bloodDonorRepository.existsBloodUserByJmbg(map.getJmbg())) {

            throw new IllegalArgumentException("Blood donor with this jmbg already exists");
        }
        map.setAddress(address);

        map.setPoints(0);
        return save(map);
    }

    @Override
    public Questionnaire getQuestionnaireFromBloodDonor(UUID bloodDonorId) {
        var BloodDonor = findById(bloodDonorId);

        if (BloodDonor.getQuestionnaire() != null) {

            return BloodDonor.getQuestionnaire();
        }

        throw new NotFoundException("Blood donor does not have a questionnaire");
    }

    @Override
    public List<BloodDonor> findAllWithAddressAndQuestionnaire() {
        return _bloodDonorRepository.findAllWithAddressAndQuestionnaire();
    }

    @Override
    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public void registerNewBloodDonor(RegisterBloodDonorDTO dto) {

        Address address = _addressService
                .saveAddresFromBloodDonorRegistration(_mapper.map(dto.getAddressBloodDonorDTO(), Address.class));
        BloodDonor bloodDonor =
                RegisterUser(_mapper.map(dto.getNonRegisteredBloodDonorInfoDTO(), BloodDonor.class), address);
        _accountService.registerRegisteredUser(_mapper.map(dto.getAccountDTO(), Account.class),
                bloodDonor);

    }
}
