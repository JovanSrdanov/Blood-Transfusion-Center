package groupJASS.ISA_2022.Service.Implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import groupJASS.ISA_2022.DTO.CoordinatesForGPSDTO;
import groupJASS.ISA_2022.DTO.CurrentHelicopterPositionDTO;
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
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${approvedBloodShipment}")
    String approvedBloodShipment;
    @Value("${setGPSCoordinates}")
    String setGPSCoordinates;
    @Value("${bloodShipmentArrived}")
    String bloodShipmentArrived;

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
    public void receiveBloodShippmentDemand(Message message) throws IOException {
        try {
            System.out.println("Consumer: " + "demandBloodShipment" + " activated");
            byte[] body = message.getBody();
            ObjectMapper mapper = new ObjectMapper();
            DemandBloodShipmentDTO demandBloodShipmentDTO = mapper.readValue(body, DemandBloodShipmentDTO.class);

            GPSDemandBloodShipment gpsDemandBloodShipment = _mapper.map(demandBloodShipmentDTO, GPSDemandBloodShipment.class);
            gpsDemandBloodShipment.setBloodCenter(_bloodCenterService.findById(demandBloodShipmentDTO.getBloodCenterId()));
            gpsDemandBloodShipment.setDemandBloodShipmentStatus(DemandBloodShipmentStatus.PENDING_DELIVERY);
            gpsDemandBloodShipment.setId(UUID.randomUUID());

            _gpsDemandBloodShipmentRepository.save(gpsDemandBloodShipment);
            System.out.println("Consumer: " + "demandBloodShipment" + ": demandBloodShipmentDTO saved");
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public Page<GPSDemandBloodShipment> getAllPendingShipments(UUID bloodCenterId, int page, int pageSize) {

        return _gpsDemandBloodShipmentRepository.getAllPendingShipments(bloodCenterId, PageRequest.of(page, pageSize));
    }

    private GPSDemandBloodShipment setShipmentStatusToInProgress(UUID shipmentId) throws BadRequestException {
        GPSDemandBloodShipment shipment = findById(shipmentId);
        shipment.setDemandBloodShipmentStatus(DemandBloodShipmentStatus.DELIVERY_IN_PROGRESS);
        return save(shipment);
    }

    @Override
    public void handleShipment(UUID shipmentId, int seconds) throws Exception {

        var shipment = setShipmentStatusToInProgress(shipmentId);
        var bloodCenter = shipment.getBloodCenter();
        if (!bloodCenter.isHelicopterHere()) {
            throw new Exception("Helicopter is not here!");
        }
        //TODO jovan uradi smanjivanje krvi
        CoordinatesForGPSDTO coordinatesForGPSDTO = new CoordinatesForGPSDTO(bloodCenter.getAddress().getLongitude(), bloodCenter.getAddress().getLatitude(), shipment.getLongitude(), shipment.getLatitude(), shipmentId, seconds);
        bloodCenter.setDeliveryInProgres(true);
        bloodCenter.setHelicopterHere(false);
        _bloodCenterService.save(bloodCenter);
        sendCordinatesToGps(coordinatesForGPSDTO);
        informHospitalThatHelicopterIsComing();

    }

    private void informHospitalThatHelicopterIsComing() {
        System.out.println("Sending information about the helicopter");

        this.rabbitTemplate.convertAndSend(approvedBloodShipment, "The helicopter with blood supplies is coming");

        System.out.println("Information about the helicopter sent");
    }

    private void sendCordinatesToGps(CoordinatesForGPSDTO coordinatesForGPSDTO) {
        System.out.println("Sendig coordinates!");

        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(CoordinatesForGPSDTO.class);
        jsonConverter.setClassMapper(classMapper);
        rabbitTemplate.setMessageConverter(jsonConverter);
        this.rabbitTemplate.convertAndSend(setGPSCoordinates, coordinatesForGPSDTO);

        System.out.println("Coordinates sent\n");

    }

    @RabbitListener(queues = "${getCurrentGPSCoordinates}")
    public void getCurrentGPSCoordinates(Message message) throws IOException {
        System.out.println("Consumer: " + "getCurrentGPSCoordinates" + "activated");
        byte[] body = message.getBody();
        ObjectMapper mapper = new ObjectMapper();
        CurrentHelicopterPositionDTO currentHelicopterPositionDTO = mapper.readValue(body, CurrentHelicopterPositionDTO.class);
        System.out.println(currentHelicopterPositionDTO);

        //TODO jovan stavi na front
    }


    @RabbitListener(queues = "${helicopterArrived}")
    public void helicopterArrived(Message message) throws IOException, BadRequestException {
        System.out.println("Consumer: " + "bloodShipmentArrived" + "activated");
        byte[] body = message.getBody();
        ObjectMapper mapper = new ObjectMapper();
        CurrentHelicopterPositionDTO currentHelicopterPositionDTO = mapper.readValue(body, CurrentHelicopterPositionDTO.class);

        System.out.println(currentHelicopterPositionDTO);

        var shipment = findById(currentHelicopterPositionDTO.getShipmentID());
        shipment.setDemandBloodShipmentStatus(DemandBloodShipmentStatus.DELIVERED);
        save(shipment);
        var bloodcenter = shipment.getBloodCenter();
        bloodcenter.setDeliveryInProgres(false);
        _bloodCenterService.save(bloodcenter);

        this.rabbitTemplate.convertAndSend(bloodShipmentArrived, "Helicopter has arrived");
        //TODO jovan stavi na front
    }


}
