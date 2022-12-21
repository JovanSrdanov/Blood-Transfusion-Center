package groupJASS.ISA_2022.Controller;


import groupJASS.ISA_2022.DTO.Account.AccountDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
public class AccountController {

    private final IAccountService _accountService;
    private final ModelMapper _mapper;

    @Autowired
    public AccountController(IAccountService accountService, ModelMapper mapper)
    {
         _accountService = accountService;
         _mapper = mapper;
    }


    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    @PostMapping("system-admin")
    public ResponseEntity registerSystemAdminAccount(@RequestBody AccountDTO dto) {
        try {
            this._accountService.registerAccount(dto,"ROLE_SYSTEM_ADMIN", null);
            //There's no point in returning just username
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}
