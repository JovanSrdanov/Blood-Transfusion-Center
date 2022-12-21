package groupJASS.ISA_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {
    @Id
    private UUID id;

    @ManyToMany
    @JoinTable(name = "appointment_staff",
            joinColumns = @JoinColumn(name = "appointment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "id"))
    private Set<Staff> staff = new HashSet<Staff>();

    @ManyToOne
    @JoinColumn(name="blood_center_id", nullable=false)
    private BloodCenter bloodCenter;

    @Column(nullable = false)
    private boolean isPremade;

    @Embedded
    DateRange time;
}
