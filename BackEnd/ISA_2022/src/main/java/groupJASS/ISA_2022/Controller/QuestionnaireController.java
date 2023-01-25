package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.QuestionnaireDTO;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.Questionnaire;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentSchedulingHistoryService;
import groupJASS.ISA_2022.Service.Interfaces.IQuestionnaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "Bearer Authentication")
public class QuestionnaireController {

    private final IQuestionnaireService _questionnaireService;
    private final ModelMapper _modelMapper;
    //@Autowired
    //private IBloodDonorService _bloodDonorService;

    private final IAppointmentSchedulingHistoryService _appointmentSchedulingHistoryService;

    @Autowired
    private IAccountService _accountService;


    @Autowired
    public QuestionnaireController(IQuestionnaireService questionnaireService,
                                   ModelMapper mapper,
                                   IAppointmentSchedulingHistoryService appointmentSchedulingHistoryService,
                                   IAccountService accountService) {
        _questionnaireService = questionnaireService;
        _modelMapper = mapper;
        //_bloodDonorService = bloodDonorService;
        _appointmentSchedulingHistoryService = appointmentSchedulingHistoryService;
        _accountService = accountService;
    }

    @Operation(summary = "Get all Questionnaires", description = "Get all Questionnaires", method="GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Questionnaire.class))))
    })
    @GetMapping
    public ResponseEntity<List<Questionnaire>> findAll() {
        var res = (List<Questionnaire>) _questionnaireService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Checks if donor can donate blood", description = "Checks if donor can donate blood for givne donor id", method="GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = boolean.class))),
            @ApiResponse(responseCode = "400", description = "Something went wrong",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Donor not found",
                    content = @Content)
    })
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

    @Operation(summary = "Gets questionaire for appointment scheduling history id", description = "Gets questionaire for appointment scheduling history id", method="GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found questionaire",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionnaireDTO.class))),
            @ApiResponse(responseCode = "400", description = "Something went wrong",
                    content = @Content)
    })
    @GetMapping("getQuestionaire/{ashId}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> getQuestionaire(@PathVariable("ashId") UUID ashId, Principal account) {
        try {
            //Account a = _accountService.findAccountByEmail(account.getName());
            Questionnaire questionnaire = _appointmentSchedulingHistoryService.findById(ashId).getBloodDonor()
                    .getQuestionnaire();
            if (questionnaire == null) {
                return new ResponseEntity<>("Donor did not fill out a questionaire",
                        HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(_modelMapper.map(questionnaire, QuestionnaireDTO.class), HttpStatus.CREATED);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Fill questionnaire", description = "Fill questionnaire with given data", method="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found questionaire",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Something went wrong",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Something went wrong",
                    content = @Content)
    })
    @PostMapping("fillQuestionare")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<?> fillQuestionnaire(@Valid @RequestBody QuestionnaireDTO dto, Principal account)
            throws ConstraintViolationException {

        try {
            Account a = _accountService.findAccountByEmail(account.getName());
            _questionnaireService.fillQuestionnaire(_modelMapper.map(dto, Questionnaire.class), a.getPersonId());
            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
