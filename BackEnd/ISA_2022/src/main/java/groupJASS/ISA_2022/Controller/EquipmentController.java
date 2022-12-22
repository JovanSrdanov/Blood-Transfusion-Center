package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Equipment.EquipmentUpdateDTO;
import groupJASS.ISA_2022.Service.Interfaces.IEquipmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.UUID;

@RestController
@RequestMapping("equipment")
public class EquipmentController {
    private final IEquipmentService _equipmentService;
    private final ModelMapper _mapper;

    @Autowired
    public EquipmentController(IEquipmentService equipmentService, ModelMapper mapper) {
        this._equipmentService = equipmentService;
        this._mapper = mapper;
    }

    @GetMapping("/{centreId}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> getEquipmentByCentre(@PathVariable("centreId") UUID centreId) {
        try {
            return new ResponseEntity<>(_equipmentService.findByCentre(centreId), HttpStatus.OK);
        }
        catch(NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> updateEquipmentInCenter(@RequestBody EquipmentUpdateDTO updatedInfo) {
        try {
            return new ResponseEntity<>(_equipmentService.updateQuantity(
                    updatedInfo.getEquipmentId(),
                    updatedInfo.getQuantity()), HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
