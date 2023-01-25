package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorGetByNameAndSurnameDto;
import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorInfoDto;
import groupJASS.ISA_2022.DTO.BloodDonor.BloodDonorLazyDTO;
import groupJASS.ISA_2022.DTO.BloodDonor.RegisterBloodDonorDTO;
import groupJASS.ISA_2022.DTO.PageEntityDto;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.*;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IActivateAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IAddressService;
import groupJASS.ISA_2022.Service.Interfaces.IBloodDonorService;
import groupJASS.ISA_2022.Utilities.MappingUtilities;
import groupJASS.ISA_2022.Utilities.ObjectMapperUtils;
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
@RequestMapping("blood-donor")
@SecurityRequirement(name = "Bearer Authentication")
public class BloodDonorController {

    private final IBloodDonorService _bloodDonorService;
    private final IAddressService _addressService;

    private final IAccountService _accountService;
    private final IActivateAccountService _activateAccountService;

    private final ModelMapper _mapper;

    @Autowired
    public BloodDonorController(IBloodDonorService bloodDonorService, ModelMapper modelMapper,
                                IAddressService addressService, IAccountService _accountService, IActivateAccountService activateAccountService) {
        this._bloodDonorService = bloodDonorService;
        this._mapper = modelMapper;
        this._addressService = addressService;
        this._accountService = _accountService;
        this._activateAccountService = activateAccountService;

    }

    @Operation(summary = "Get all blood donors", description = "Get all blood donors", method="GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BloodDonor.class))))
    })
    @GetMapping
    public ResponseEntity<List<BloodDonor>> findAllWithAddressAndQuestionnaire() {
        return new ResponseEntity<>(this._bloodDonorService.findAllWithAddressAndQuestionnaire(), HttpStatus.OK);
    }

    @Operation(summary = "Get all blood donors lazy load", description = "Get all blood donors using lazy loading", method="GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BloodDonorLazyDTO.class))))
    })
    @GetMapping("lazy")
    public ResponseEntity<List<BloodDonorLazyDTO>> findAllLazy() {
        return new ResponseEntity<>(MappingUtilities.mapList((List<BloodDonor>) this._bloodDonorService.findAll(), BloodDonorLazyDTO.class, _mapper), HttpStatus.OK);
    }

    @Operation(summary = "Get info for loggined in donor", description = "Get info for loggined in donor", method="GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BloodDonorInfoDto.class)))
    })
    @GetMapping("my-info")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<BloodDonorInfoDto> findById(Principal account) {
        Account a = _accountService.findAccountByEmail(account.getName());
        BloodDonorInfoDto userDto = new BloodDonorInfoDto(_bloodDonorService.findById(a.getPersonId()), a.getEmail());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Operation(summary = "Save donor info", description = "Save or update donor info", method="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BloodDonor.class)))
    })
    @PostMapping
    public ResponseEntity<BloodDonor> save(@RequestBody BloodDonor entity) {
        try {
            return new ResponseEntity<>(this._bloodDonorService.save(entity), HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update donor info", description = "Update donor info", method="PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Updated donor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BloodDonorInfoDto.class))),
            @ApiResponse(responseCode = "400", description = "Something went wrong", content = @Content),
            @ApiResponse(responseCode = "404", description = "Donor not found", content = @Content)
    })
    @PatchMapping("update")
    @PreAuthorize("hasRole('BLOOD_DONOR')")
    public ResponseEntity<BloodDonorInfoDto> update(@Valid @RequestBody BloodDonorInfoDto updatedUserDto) {
        BloodDonor updatedDonor = _mapper.map(updatedUserDto, BloodDonor.class);

        try {
            BloodDonor entity = _bloodDonorService.updateDonorInfo(
                    ObjectMapperUtils.map(updatedUserDto.getAddress(), Address.class), updatedDonor);
            Account acc = _accountService.findAccountByPersonId(entity.getId());
            BloodDonorInfoDto dto = new BloodDonorInfoDto(entity, acc.getEmail());
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Register new donor", description = "Register new donor", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterBloodDonorDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Something went wrong",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Something went wrong",
                    content = @Content)
    })
    @PostMapping("register")
    public ResponseEntity<?> Register(@Valid @RequestBody RegisterBloodDonorDTO dto)
            throws ConstraintViolationException {

        try {
            var account = _bloodDonorService.registerNewBloodDonor(dto);
            _bloodDonorService.sendActvivationToken(_activateAccountService.save(new ActivateAccount(UUID.randomUUID(), account.getEmail(), UUID.randomUUID(), account.getId())));
            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get donor by name or surname", description = "Get donor by name or surname", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterBloodDonorDTO.class)) })
    })
    @PostMapping("get-by-name-surname")
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    public ResponseEntity<PageEntityDto<BloodDonorInfoDto>> getByNameAndSurname(@RequestBody BloodDonorGetByNameAndSurnameDto dto) {
        PageEntityDto<List<BloodDonorInfoDto>> bloodDonors = _bloodDonorService.findBloodDonorByNameAndSurname(dto);
        return new ResponseEntity(bloodDonors, HttpStatus.OK);
    }

    @Operation(summary = "Get donor by search parameters", description = "Get donor by name, surname, center, status...", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found donors",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BloodDonorInfoDto.class))))
    })
    @PostMapping("get-by-name-surname-for-center-and-status/{status}")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity<PageEntityDto<BloodDonorInfoDto>> getByNameAndSurnameForCenter(@RequestBody BloodDonorGetByNameAndSurnameDto dto,
                                                                                         @PathVariable("status") AppointmentSchedulingConfirmationStatus status ,
                                                                                         Principal principal) {
        PageEntityDto<List<BloodDonorInfoDto>> bloodDonors = _bloodDonorService.findBloodDonorByNameAndSurnameForCenterAndStatus(dto, status, principal);
        return new ResponseEntity(bloodDonors, HttpStatus.OK);
    }
}