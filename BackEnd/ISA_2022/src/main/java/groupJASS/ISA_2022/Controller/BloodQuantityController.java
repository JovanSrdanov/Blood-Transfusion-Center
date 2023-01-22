package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Blood.BloodInfoDto;
import groupJASS.ISA_2022.DTO.Blood.BloodUpdateDto;
import groupJASS.ISA_2022.Exceptions.CenterOutOfBloodException;
import groupJASS.ISA_2022.Exceptions.CenterOutOfEquipmentException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Model.BloodQuantity;
import groupJASS.ISA_2022.Model.Staff;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodQuantityService;
import groupJASS.ISA_2022.Service.Interfaces.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("blood-quantity")
public class BloodQuantityController {
    private final IBloodQuantityService _bloodQuantityService;
    private final IAccountService _accountService;
    private final IStaffService _staffService;

    @Autowired
    public BloodQuantityController(IBloodQuantityService bloodQuantityService,
                                   IAccountService accountService,
                                   IStaffService staffService) {
        this._bloodQuantityService = bloodQuantityService;
        this._accountService = accountService;
        this._staffService = staffService;
    }

    @GetMapping("get-by-centre")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> getBloodQuantityByCentre(Principal account) {
        try {
            List<BloodInfoDto> result = new ArrayList<>();

            Account a = _accountService.findAccountByEmail(account.getName());
            Staff staff = _staffService.findById(a.getPersonId());

            Set<BloodQuantity> quantityList = staff.getBloodCenter().getBloodQuantities();

            for (var item : quantityList) {
                result.add(new BloodInfoDto(item));
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch(NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> updateBloodQuantityInCenter(@RequestBody BloodUpdateDto updatedInfo) {
        try {
            BloodQuantity updatedQuantity = _bloodQuantityService.updateQuantity(
                    updatedInfo.getBloodId(),
                    updatedInfo.getQuantity());
            return new ResponseEntity<>(updatedQuantity.getQuantity(), HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (CenterOutOfBloodException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
