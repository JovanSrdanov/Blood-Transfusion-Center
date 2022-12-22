package groupJASS.ISA_2022.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Equipment {
    @Id
    private UUID id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public double quantity;

    @ManyToOne
    @JoinColumn(name="blood_center_id", nullable = false)
    private BloodCenter bloodCenter;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AppointmentReportEquipmentQuantity> usingAppointments;

    public void update(Equipment updated) {
        //TODO add more if needed
        this.name = updated.name;
        this.quantity = updated.quantity;
    }
}
