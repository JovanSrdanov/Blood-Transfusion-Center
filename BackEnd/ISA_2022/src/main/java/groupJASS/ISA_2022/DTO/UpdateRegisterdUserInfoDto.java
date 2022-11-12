package groupJASS.ISA_2022.DTO;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.Gender;

import java.util.UUID;

public class UpdateRegisterdUserInfoDto {
    private UUID id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String institution;
    private String jmbg;
    private Gender gender;
    private Address address;
}
