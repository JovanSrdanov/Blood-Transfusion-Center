package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.QuestionnaireDTO;
import groupJASS.ISA_2022.Model.Questionnaire;
import groupJASS.ISA_2022.Service.Implementations.QuestionnaireService;
import groupJASS.ISA_2022.Service.Interfaces.IQuestionnaireService;
import groupJASS.ISA_2022.Utilities.MappingUtilities;
import groupJASS.ISA_2022.Utilities.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("no-auth-test")
public class NoAuthTestController {

   private final IQuestionnaireService _questionnaireService;

   @Autowired
   public NoAuthTestController(IQuestionnaireService questionnaireService)
   {
      _questionnaireService  = questionnaireService;
   }

    @GetMapping
    public ResponseEntity noAuthTest(){
       Collection<Questionnaire> res  = (Collection<Questionnaire>) _questionnaireService.findAll();
       var dtos =  ObjectMapperUtils.mapAll(res, QuestionnaireDTO.class);
       return new ResponseEntity(res, HttpStatus.OK);
    }
}
