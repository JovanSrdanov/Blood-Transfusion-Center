package groupJASS.ISA_2022.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime issuingDate;

    @Column(nullable = false)
    private AppointmentSchedulingConfirmationStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blood_donor_id")
    private BloodDonor bloodDonor;

    @OneToOne(mappedBy = "appointmentSchedulingHistory")
    private AppointmentReport appointmentReport;

    public AppointmentSchedulingHistory(UUID id, String QRcode, LocalDateTime issuingDate,
                                        AppointmentSchedulingConfirmationStatus status,
                                        Appointment appointment, BloodDonor bloodDonor,
                                        AppointmentReport appointmentReport) {
        this.id = id;
        this.QRcode = QRcode;
        this.issuingDate = issuingDate;
        this.status = status;
        this.appointment = appointment;
        this.bloodDonor = bloodDonor;
        this.appointmentReport = appointmentReport;
    }
}
