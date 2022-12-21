package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.AppointmentSchedulingHistory;
import groupJASS.ISA_2022.Repository.AppointmentSchedulingHistoryRepository;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentSchedulingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
@Primary
public class AppointmentSchedulingHistoryService implements IAppointmentSchedulingHistoryService {
    private final AppointmentSchedulingHistoryRepository _appointmentSchedulingHistoryRepository;

    @Autowired
    public AppointmentSchedulingHistoryService(AppointmentSchedulingHistoryRepository appointmentSchedulingHistoryRepository) {
        _appointmentSchedulingHistoryRepository = appointmentSchedulingHistoryRepository;

    }

    @Override
    public Iterable<AppointmentSchedulingHistory> findAll() {
        return _appointmentSchedulingHistoryRepository.findAll();
    }

    @Override
    public AppointmentSchedulingHistory findById(UUID id) {
        if (_appointmentSchedulingHistoryRepository.findById(id).isPresent()) {

            return _appointmentSchedulingHistoryRepository.findById(id).get();
        }

        throw new NotFoundException("AppointmentSchedulingHistory not found");
    }

    @Override
    public AppointmentSchedulingHistory save(AppointmentSchedulingHistory entity) throws BadRequestException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        return _appointmentSchedulingHistoryRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _appointmentSchedulingHistoryRepository.deleteById(id);
    }

    @Override
    public Page<AppointmentSchedulingHistory> bloodDonorPendingAppointments(UUID personId, int page, String field, String sort) {

        //        if (sort.isBlank()) {
//            page = _appointmentSchedulingHistoryRepository.searchBy(personId, PageRequest.of(offset, pageSize));
//        } else if (sort.equals("asc")) {
//            page = _appointmentSchedulingHistoryRepository.searchBy(personId, PageRequest.of(offset, pageSize)
//                    .withSort(Sort.by(Sort.Direction.ASC, field)));
//        } else if (sort.equals("desc")) {
//            page = _appointmentSchedulingHistoryRepository.searchBy(personId, PageRequest.of(offset, pageSize)
//                    .withSort(Sort.by(Sort.Direction.DESC, field)));
//        } else {
//            throw new SortNotFoundException("This sort type doesn't exist");
//        }
//        return page;


        return null;
    }

}
