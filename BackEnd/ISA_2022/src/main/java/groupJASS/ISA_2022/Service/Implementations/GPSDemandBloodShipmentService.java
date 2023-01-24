package groupJASS.ISA_2022.Service.Implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import groupJASS.ISA_2022.DTO.DemandBloodShipmentDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.DemandBloodShipmentStatus;
import groupJASS.ISA_2022.Model.GPSDemandBloodShipment;
import groupJASS.ISA_2022.Repository.GPSDemandBloodShipmentRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import groupJASS.ISA_2022.Service.Interfaces.IGPSDemandBloodShipmentService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.UUID;

@Service
public class GPSDemandBloodShipmentService implements IGPSDemandBloodShipmentService {
    private final GPSDemandBloodShipmentRepository _gpsDemandBloodShipmentRepository;
    private final IBloodCenterService _bloodCenterService;
    private final ModelMapper _mapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public GPSDemandBloodShipmentService(GPSDemandBloodShipmentRepository gpsDemandBloodShipmentRepository, ModelMapper modelMapper, IBloodCenterService bloodCenterService) {
        _gpsDemandBloodShipmentRepository = gpsDemandBloodShipmentRepository;
        _mapper = modelMapper;
        _bloodCenterService = bloodCenterService;
    }

    @Override
    public Iterable<GPSDemandBloodShipment> findAll() {
        return _gpsDemandBloodShipmentRepository.findAll();
    }

    @Override
    public GPSDemandBloodShipment findById(UUID id) {
        if (_gpsDemandBloodShipmentRepository.findById(id).isPresent()) {

            return _gpsDemandBloodShipmentRepository.findById(id).get();
        }

        throw new NotFoundException("GPSDemandBloodShipment not found");
    }

    @Override
    public GPSDemandBloodShipment save(GPSDemandBloodShipment entity) throws BadRequestException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        return _gpsDemandBloodShipmentRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _gpsDemandBloodShipmentRepository.deleteById(id);
    }


    @RabbitListener(queues = "${demandBloodShipment}")
    public void receiveBloodShipmentDemand(Message message) throws IOException {
        System.out.println("Consumer: " + "demandBloodShipment " + "activated");
        byte[] body = message.getBody();
        ObjectMapper mapper = new ObjectMapper();
        DemandBloodShipmentDTO demandBloodShipmentDTO = mapper.readValue(body, DemandBloodShipmentDTO.class);

        GPSDemandBloodShipment gpsDemandBloodShipment = _mapper.map(demandBloodShipmentDTO, GPSDemandBloodShipment.class);
        gpsDemandBloodShipment.setDemandBloodShipmentStatus(DemandBloodShipmentStatus.PENDING_DELIVERY);
        gpsDemandBloodShipment.setBloodCenter(_bloodCenterService.findById(demandBloodShipmentDTO.getBloodCenterId()));
        gpsDemandBloodShipment.setId(UUID.randomUUID());

        _gpsDemandBloodShipmentRepository.save(gpsDemandBloodShipment);
    }

    @Override
    public Page<GPSDemandBloodShipment> getAllPendingShipments(UUID bloodCenterId, int page, int pageSize) {

        return _gpsDemandBloodShipmentRepository.getAllPendingShipments(bloodCenterId, PageRequest.of(page, pageSize));
    }
}
