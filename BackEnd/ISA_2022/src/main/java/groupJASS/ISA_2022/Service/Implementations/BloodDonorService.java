package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorInfoDto;
import groupJASS.ISA_2022.DTO.BloodDonor.RegisterBloodDonorDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodDonor;
import groupJASS.ISA_2022.Model.Questionnaire;
import groupJASS.ISA_2022.Repository.AccountRepository;
import groupJASS.ISA_2022.Repository.BloodDonorRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAddressService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodDonorService;
import groupJASS.ISA_2022.Utilities.MappingUtilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
public class BloodDonorService implements IBloodDonorService {

    private final BloodDonorRepository _bloodDonorRepository;
    private final IAddressService _addressService;
    private final IAccountService _accountService;
    private final ModelMapper _mapper;
    private final AccountRepository _accountRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public BloodDonorService(BloodDonorRepository bloodDonorRepository, IAddressService addressService, IAccountService accountService, ModelMapper mapper, AccountRepository accountRepository) {
        _bloodDonorRepository = bloodDonorRepository;
        _addressService = addressService;
        _accountService = accountService;
        _mapper = mapper;
        _accountRepository = accountRepository;
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

        throw new NotFoundException("Blood donor found");
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
        map.setPenalties(0);
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
    @Transactional(rollbackFor = Exception.class)
    public void registerNewBloodDonor(RegisterBloodDonorDTO dto) {

        Address address = _addressService
                .saveAddresFromBloodDonorRegistration(_mapper.map(dto.getAddressBloodDonorDTO(), Address.class));
        BloodDonor bloodDonor =
                RegisterUser(_mapper.map(dto.getNonRegisteredBloodDonorInfoDTO(), BloodDonor.class), address);
        _accountService.registerRegisteredUser(_mapper.map(dto.getAccountDTO(), Account.class),
                bloodDonor);


    }

    @Async
    public void sendActvivationToken(RegisterBloodDonorDTO dto) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(dto.getAccountDTO().getEmail());
        mail.setFrom("ISA_BEJBI");
        mail.setSubject("Account activation " + LocalDate.now());
        mail.setText("Go to the link to activate your account: ");
        javaMailSender.send(mail);

        System.out.println("Email poslat!");
    }

    @Override
    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public BloodDonor updateDonorInfo(Address address, BloodDonor updatedBloodDonor) throws BadRequestException {
        //TODO ovde provera sa jwt obavezno
        _addressService.save(address);
        BloodDonor oldDonor = findById(updatedBloodDonor.getId());
        oldDonor.update(updatedBloodDonor);
        return save(oldDonor);
    }

    @Override
    public List<BloodDonorInfoDto> findBloodDonorByNameAAndSurname(String name, String surname) {
        List<BloodDonor> bloodDonors = (List<BloodDonor>) _bloodDonorRepository.searchByNameAndSurnameIgnoreCase(name, surname);
        List<BloodDonorInfoDto> dtos = MappingUtilities.mapList(bloodDonors, BloodDonorInfoDto.class, _mapper);
        List<BloodDonorInfoDto> res = dtos
                .stream()
                .map(dto -> {
                    Account acc = _accountRepository.findAccountByPersonId(dto.getId());
                    String mail = "mail@mail.com";
                    if (acc != null) {
                        mail = acc.getEmail();
                    }
                    dto.setEmail(mail);
                    return dto;
                })
                .collect(Collectors.toList());
        return res;
    }
}
