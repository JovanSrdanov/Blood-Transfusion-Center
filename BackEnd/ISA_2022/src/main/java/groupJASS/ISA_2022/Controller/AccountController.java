package groupJASS.ISA_2022.Controller;


import groupJASS.ISA_2022.DTO.Account.AccountDTO;
import groupJASS.ISA_2022.DTO.Account.UpdatePasswordDto;
import groupJASS.ISA_2022.DTO.Auth.Jwt;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Utilities.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.security.Principal;

@RestController
@RequestMapping("account")
public class AccountController {

    private final IAccountService _accountService;
    private final ModelMapper _mapper;
    private final TokenUtils _tokenUtils;

    @Autowired
    public AccountController(IAccountService accountService, ModelMapper mapper, TokenUtils tokenUtils)
    {
         _accountService = accountService;
         _mapper = mapper;
         _tokenUtils = tokenUtils;
    }


    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    @PostMapping("system-admin")
    public ResponseEntity registerSystemAdminAccount(@RequestBody AccountDTO dto) {
        try {
            _accountService.registerAccount(dto,"ROLE_SYSTEM_ADMIN", null);
            //There's no point in returning just username
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    //Returns JWT with updated claim lastPasswordUpdateDate
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    @PatchMapping("password")
    public ResponseEntity updatePassword(@Validated  @RequestBody UpdatePasswordDto dto, Principal principal) {
        try {
            String email = principal.getName();
            _accountService.updatePassword(email, dto.getNewPassword());

            //Generating token
            Account account = _accountService.findAccountByEmail(email);
            String jwt = _tokenUtils.generateToken(account.getUsername(), account.getRoles().get(0).getName(), account.getLastPasswordUpdateDate());
            return new ResponseEntity<Jwt>(new Jwt(jwt), HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
