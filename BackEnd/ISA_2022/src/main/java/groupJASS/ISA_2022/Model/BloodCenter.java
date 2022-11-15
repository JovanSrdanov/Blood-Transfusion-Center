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
public class BloodCenter {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double rating;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="blood_center_id")
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "bloodCenter", fetch = FetchType.LAZY)
    private Set<BloodAdmin> staff = new HashSet<>();

    @Embedded
    private WorkingHours workingHours;
}























