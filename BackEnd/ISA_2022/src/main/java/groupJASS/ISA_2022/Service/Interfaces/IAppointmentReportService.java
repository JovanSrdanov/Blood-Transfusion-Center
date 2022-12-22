package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.AppointmentReport;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IAppointmentReportService extends ICrudService<AppointmentReport> {
    Page<AppointmentReport> getBloodDonorReports(UUID bloodDonorId, int page, int pageSize, String sort) throws SortNotFoundException;
}
