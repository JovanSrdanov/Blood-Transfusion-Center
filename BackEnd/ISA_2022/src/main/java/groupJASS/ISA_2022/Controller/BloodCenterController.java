package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.BloodCenterRegistrationDTO;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("blood-center")
public class BloodCenterController {

    private final IBloodCenterService _bloodCenterService;
    private final ModelMapper _mapper;

    @Autowired
    public BloodCenterController(IBloodCenterService bloodCenterService, ModelMapper mapper)
    {
       _bloodCenterService = bloodCenterService;
        _mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> registerBloodCenter(@RequestBody BloodCenterRegistrationDTO dto)
    {
       try{
           _bloodCenterService.save(_mapper.map(dto, BloodCenter.class));
           return new ResponseEntity<>(HttpStatus.CREATED);
       }
       catch (Exception e)
       {
           return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
}
