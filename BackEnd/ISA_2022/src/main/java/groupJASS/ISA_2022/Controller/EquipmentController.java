package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Equipment.EquipmentBasicInfo;
import groupJASS.ISA_2022.DTO.Equipment.EquipmentUpdateDTO;
import groupJASS.ISA_2022.Exceptions.CenterOutOfEquipmentException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.Equipment;
import groupJASS.ISA_2022.Model.Staff;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IEquipmentService;
import groupJASS.ISA_2022.Service.Interfaces.IStaffService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("equipment")
public class EquipmentController {
    private final IEquipmentService _equipmentService;
    private final IAccountService _accountService;
    private final IStaffService _staffService;
    private final ModelMapper _mapper;

    @Autowired
    public EquipmentController(IEquipmentService equipmentService, ModelMapper mapper,
                               IAccountService accountService, IStaffService staffService) {
        this._equipmentService = equipmentService;
        this._accountService = accountService;
        this._staffService = staffService;
        this._mapper = mapper;
    }

    @GetMapping("get-by-centre")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> getEquipmentByCentre(Principal account) {
        try {
            List<EquipmentBasicInfo> result = new ArrayList<>();

            Account a = _accountService.findAccountByEmail(account.getName());
            Staff staff = _staffService.findById(a.getPersonId());

            List<Equipment> equipmentList = _equipmentService.findByCentre(staff.getBloodCenter().getId());

            for(Equipment item : equipmentList) {
                result.add(new EquipmentBasicInfo(item));
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch(NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> updateEquipmentInCenter(@RequestBody EquipmentUpdateDTO updatedInfo) {
        try {
            Equipment updatedEquipment = _equipmentService.updateQuantity(
                    updatedInfo.getEquipmentId(),
                    updatedInfo.getQuantity());
            return new ResponseEntity<>(updatedEquipment.getQuantity(), HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (CenterOutOfEquipmentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
