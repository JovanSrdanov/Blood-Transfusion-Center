package groupJASS.ISA_2022.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BloodDonor extends Person {

    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;
    @Column(nullable = false)
    private String institution;
    @Column(nullable = false)
    private String occupation;
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{13}", message = "length must be 13 and it can only contain numbers")
    private String jmbg;
    @Column(nullable = false)
    private Gender gender;
    @Column(nullable = false)
    private int points;
    @OneToOne(fetch = FetchType.LAZY)
    private Questionnaire questionnaire;

    @JsonIgnore
    @OneToMany(mappedBy = "bloodDonor", fetch = FetchType.LAZY)
    private Set<AppointmentSchedulingHistory> appointmentSchedulingHistories;

    private int penalties;

    public void update(BloodDonor updated) {
        this.institution = updated.getInstitution();
        this.jmbg = updated.getJmbg();
        this.gender = updated.getGender();
        this.name = updated.getName();
        this.surname = updated.getSurname();
        this.phoneNumber = updated.getPhoneNumber();
        this.occupation = updated.getOccupation();
    }


}
