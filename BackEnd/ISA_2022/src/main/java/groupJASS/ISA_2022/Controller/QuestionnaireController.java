package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.QuestionnaireDTO;
import groupJASS.ISA_2022.Model.Questionnaire;
import groupJASS.ISA_2022.Service.Interfaces.IQuestionnaireService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("questionnaire")
public class QuestionnaireController {

    private final IQuestionnaireService _questionnaireService;

    private final ModelMapper _modelMapper;

    @Autowired
    public QuestionnaireController(IQuestionnaireService questionnaireService, ModelMapper mapper) {
        _questionnaireService = questionnaireService;
        _modelMapper = mapper;
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

    @PostMapping("fillQuestionare")
    public ResponseEntity<?> fillQuestionare(@Valid @RequestBody QuestionnaireDTO dto)
            throws ConstraintViolationException {

        //TODO dodati id blooddonora
        try {

            UUID bloodDonorId = UUID.randomUUID();
            _questionnaireService.fillQuestionare(_modelMapper.map(dto, Questionnaire.class), bloodDonorId);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
