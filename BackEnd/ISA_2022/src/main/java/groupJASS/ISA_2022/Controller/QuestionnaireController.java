package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.QuestionnaireDTO;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.Questionnaire;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodDonorService;
import groupJASS.ISA_2022.Service.Interfaces.IQuestionnaireService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("questionnaire")
public class QuestionnaireController {

    private final IQuestionnaireService _questionnaireService;
    private final ModelMapper _modelMapper;
    @Autowired
    private IBloodDonorService _bloodDonorService;

    @Autowired
    private IAccountService _accountService;


    @Autowired
    public QuestionnaireController(IQuestionnaireService questionnaireService, ModelMapper mapper, IBloodDonorService bloodDonorService, IAccountService accountService) {
        _questionnaireService = questionnaireService;
        _modelMapper = mapper;
        _bloodDonorService = bloodDonorService;
        _accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Questionnaire>> findAll() {
        var res = (List<Questionnaire>) _questionnaireService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("canDonateBlood/{bloodDonorId}")
    public ResponseEntity<?> canDonateBlood(@PathVariable UUID bloodDonorId) {
        try {
            return new ResponseEntity<>(_questionnaireService.canDonateBlood(bloodDonorId), HttpStatus.OK);

        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getQuestionaire/{bloodDonorId}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> getQuestionaire(@PathVariable("bloodDonorId") UUID bloodDonorId, Principal account) {
        try {
            //Account a = _accountService.findAccountByEmail(account.getName());
            Questionnaire questionnaire = _bloodDonorService.findById(bloodDonorId).getQuestionnaire();
            return new ResponseEntity<>(_modelMapper.map(questionnaire, QuestionnaireDTO.class), HttpStatus.CREATED);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("fillQuestionare")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> fillQuestionare(@Valid @RequestBody QuestionnaireDTO dto, Principal account)
            throws ConstraintViolationException {
        
        try {
            Account a = _accountService.findAccountByEmail(account.getName());
            _questionnaireService.fillQuestionare(_modelMapper.map(dto, Questionnaire.class), a.getPersonId());
            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
