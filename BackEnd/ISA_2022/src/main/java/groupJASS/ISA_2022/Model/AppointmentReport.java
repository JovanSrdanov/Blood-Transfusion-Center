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
public class AppointmentReport {
    @Id
    private UUID id;

    //TODO skontaj koje sve info treba (odlicno  pitanje kolega)
    private String text;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AppointmentReportEquipmentQuantity> usedEquipment;

    @OneToOne
    @JoinColumn
    private Appointment appointment;
}
