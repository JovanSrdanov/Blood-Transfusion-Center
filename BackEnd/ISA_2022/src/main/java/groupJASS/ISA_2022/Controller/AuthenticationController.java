package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Account.ActivateAccountDTO;
import groupJASS.ISA_2022.DTO.Auth.Jwt;
import groupJASS.ISA_2022.DTO.Auth.JwtAuthenticationRequest;
import groupJASS.ISA_2022.Model.Account;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Utilities.TokenUtils;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Log in", description = "Retruns JWT for given username and password", method="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in, JWT:",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Something went wrong",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Account is not activated or password and username are invalid",
                    content = @Content)
    })
    @PostMapping("/login")
    @RateLimiter(name = "loginService", fallbackMethod = "loginRateLimiterFallbackMethod")
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

    public ResponseEntity<String> loginRateLimiterFallbackMethod(RequestNotPermitted rqnp) {
        return new ResponseEntity<>("The servers are currently busy, please try again later",
                HttpStatus.TOO_MANY_REQUESTS);
    }

    @Operation(summary = "Activates account", description = "Activates account for given activation code and account id", method="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account activated",
                    content = @Content(mediaType = "application/json"))
    })

    @PostMapping("/activate-account")
    public ResponseEntity<?> activateAccount(@RequestBody ActivateAccountDTO activateAccountDTO) {
        try {
            Account account = accountService.activateAccount(activateAccountDTO);
            return ResponseEntity.ok("Succes");

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}


