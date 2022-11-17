package groupJASS.ISA_2022.Model;

import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class AppointmentSchedulingConfirmation {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String QRcode;

    @Column(nullable = false)
    private LocalDate issuingDate;

    @Column(nullable = false)
    private AppointmentSchedulingConfirmationStatus status;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
