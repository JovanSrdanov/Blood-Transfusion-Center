package groupJASS.ISA_2022.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class AppointmentSchedulingHistory {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String QRcode;

    @Column(nullable = false)
    private LocalDate issuingDate;

    @Column(nullable = false)
    private AppointmentSchedulingConfirmationStatus status;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "blood_donor_id")
    private BloodDonor bloodDonor;

    @OneToOne(mappedBy = "appointmentSchedulingHistory")
    private AppointmentReport appointmentReport;
}
