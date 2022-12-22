package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.AppointmentReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentReportRepository extends JpaRepository<AppointmentReport, UUID> {
}
