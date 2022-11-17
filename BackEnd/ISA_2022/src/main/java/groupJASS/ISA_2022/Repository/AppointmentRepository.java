package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
}
