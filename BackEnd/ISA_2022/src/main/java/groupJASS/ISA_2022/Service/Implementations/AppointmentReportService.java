package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Exceptions.SortNotFoundException;
import groupJASS.ISA_2022.Model.AppointmentReport;
import groupJASS.ISA_2022.Repository.AppointmentReportRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentReportService;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
@Primary
public class AppointmentReportService implements IAppointmentReportService {
    private final AppointmentReportRepository _appointmentReportRepository;

    public AppointmentReportService(AppointmentReportRepository _appointmentReportRepository) {
        this._appointmentReportRepository = _appointmentReportRepository;
    }


    @Override
    public Iterable<AppointmentReport> findAll() {
        return _appointmentReportRepository.findAll();
    }

    @Override
    public AppointmentReport findById(UUID id) {
        if (_appointmentReportRepository.findById(id).isPresent()) {
            return _appointmentReportRepository.findById(id).get();
        }

        throw new NotFoundException("Report found");
    }

    @Override
    public AppointmentReport save(AppointmentReport entity) throws BadRequestException {
        AppointmentReport report;
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
            report = _appointmentReportRepository.save(entity);
        } else {
            AppointmentReport oldReport = findById(entity.getId());
            if (oldReport == null) {
                throw new NotFoundException("Report not found");
            }
            oldReport.update(entity);
            report = _appointmentReportRepository.save(oldReport);
        }

        return report;
    }

    @Override
    public void deleteById(UUID id) {
        _appointmentReportRepository.deleteById(id);
    }

    @Override
    public Page<AppointmentReport> getBloodDonorReports(UUID bloodDonorId, int page, int pageSize, String sort) throws SortNotFoundException {

        Page<AppointmentReport> currentPage;
        if (sort.isBlank()) {
            currentPage = _appointmentReportRepository.searchBy(bloodDonorId, PageRequest.of(page, pageSize));
        } else if (sort.equals("asc")) {
            currentPage = _appointmentReportRepository.searchByAsc(bloodDonorId, PageRequest.of(page, pageSize));
        } else if (sort.equals("desc")) {
            currentPage = _appointmentReportRepository.searchByDesc(bloodDonorId, PageRequest.of(page, pageSize));
        } else {
            throw new SortNotFoundException("This sort type doesn't exist");
        }
        return currentPage;
    }
}
