package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Account.ActivateAccountDTO;
import groupJASS.ISA_2022.DTO.Auth.Jwt;
import groupJASS.ISA_2022.DTO.Auth.JwtAuthenticationRequest;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Utilities.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IAccountService accountService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        try {
            // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
            // AuthenticationException
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword()));

            // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
            // kontekst
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Kreiraj token za tog korisnika
            Account account = (Account) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(account.getUsername(), account.getRoles().get(0).getName(), account.getLastPasswordUpdateDate());
            int expiresIn = tokenUtils.getExpiredIn();

            // Vrati token kao odgovor na uspesnu autentifikaciju
            return ResponseEntity.ok(new Jwt(jwt));
        } catch (DisabledException e) {
            return new ResponseEntity<>("Account is not activated", HttpStatus.FORBIDDEN);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/activate-account")
    public ResponseEntity<?> activateAccount(@RequestBody ActivateAccountDTO activateAccountDTO) {
        try {

            Account account = accountService.activateAccount(activateAccountDTO);
            String jwt = tokenUtils.generateToken(account.getUsername(), account.getRoles().get(0).getName(), account.getLastPasswordUpdateDate());
            int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new Jwt(jwt));

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }
}


