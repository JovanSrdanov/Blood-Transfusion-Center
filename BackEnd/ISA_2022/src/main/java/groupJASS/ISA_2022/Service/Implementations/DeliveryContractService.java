package groupJASS.ISA_2022.Service.Implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import groupJASS.ISA_2022.DTO.DeliveryContract.ContractDTO;
import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.BloodQuantity;
import groupJASS.ISA_2022.Model.DeliveryContract;
import groupJASS.ISA_2022.Repository.BloodQuantityRepository;
import groupJASS.ISA_2022.Repository.DeliveryContractRepository;
import groupJASS.ISA_2022.Service.Interfaces.IDeliveryContractService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class DeliveryContractService implements IDeliveryContractService {
    private final DeliveryContractRepository _deliveryContractRepository;
    private final BloodQuantityRepository _bloodQuantityRepository;
    private final ModelMapper _mapper;
    private final RabbitTemplate _template;

    @Autowired
    public DeliveryContractService(DeliveryContractRepository deliveryContractRepository,
                                   BloodQuantityRepository bloodQuantityRepository,
                                   ModelMapper mapper,
                                   RabbitTemplate template) {
        this._deliveryContractRepository = deliveryContractRepository;
        this._bloodQuantityRepository = bloodQuantityRepository;
        this._mapper = mapper;
        this._template = template;
    }

    @Override
    public Iterable<DeliveryContract> findAll() {
        return _deliveryContractRepository.findAll();
    }

    @Override
    public DeliveryContract findById(UUID id) {
        if (_deliveryContractRepository.findById(id).isPresent()) {
            return _deliveryContractRepository.findById(id).get();
        }

        throw new NotFoundException("Delivery not found");
    }

    @Override
    public DeliveryContract save(DeliveryContract entity) throws BadRequestException {
//        var oldContract = _deliveryContractRepository.findById(entity.getId());
//        if (oldContract.isPresent()) {
//            DeliveryContract updatedContract = oldContract.get();
//            updatedContract = entity;
//
//            //TODO proveri dal cuva kako treba
//            return _deliveryContractRepository.save(updatedContract);
//        }
        return _deliveryContractRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _deliveryContractRepository.deleteById(id);
    }

    @RabbitListener(queues = "${createContract}")
    public void receiveCreateContractRequest(Message message){
        try {
            System.out.println("Consumer queue: " + "createContract " + "activated");

            byte[] body = message.getBody();
            ObjectMapper mapper = new ObjectMapper();
            ContractDTO contract = mapper.readValue(body, ContractDTO.class);

            DeliveryContract newContract = _mapper.map(contract, DeliveryContract.class);
            save(newContract);

            System.out.println("\n**********CONTRACT SUCCESSFULLY CREATED AND SAVED**********\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    //trenutno podeseno da se okida na svakih 15 sekundi, TODO promeniti u dane
//    @Scheduled(cron = "*/15 * * * * *")
    //Everyday at 05:00
    //@Scheduled(cron = "0 0 5 * * *")
    @Scheduled(cron = "*/15 * * * * *")
    public void sendDelivery() {
        System.out.println("Producer queue: " + "deliveryResponse" + " activated");

        List<DeliveryContract> allContracts = _deliveryContractRepository.findAll();
        for (var contract : allContracts) {
            if (isTwoDaysBefore(contract.getDeliveryDay())) {
                if (!hasEnoughBlood(contract, false)) {
                    sendNegativeResponse(contract.getQueueName());
                }
            }
            else if(isDeliveryDay(contract)){
                if(hasEnoughBlood(contract, true)) {
                    sendPositiveResponse(contract.getQueueName());
                }
            }
        }
    }

    private boolean isTwoDaysBefore(int deliveryDay) {
        return deliveryDay - LocalDate.now().getDayOfMonth() == 2;
    }

    private boolean isDeliveryDay(DeliveryContract contract) {
        return LocalDate.now().getDayOfMonth() == contract.getDeliveryDay();
    }

    private boolean hasEnoughBlood(DeliveryContract contract, boolean isDeliveryDay) {
        List<BloodQuantity> allQuantities = _bloodQuantityRepository.findAll();

        for (var quantity: allQuantities) {
            if (quantity.getBloodGroup() == contract.getBloodGroup()
                    && quantity.getQuantity() - contract.getQuantity() >= 0) {
                if (isDeliveryDay) {
                    quantity.setQuantity(quantity.getQuantity() - contract.getQuantity());
                    _bloodQuantityRepository.save(quantity);
                }
                return true;
            }
        }
        return false;
    }

    private void sendPositiveResponse(String routingKey) {
        _template.convertAndSend(routingKey,
                "Scheduled delivery will be completed today.");
    }

    private void sendNegativeResponse(String routingKey) {
        _template.convertAndSend(routingKey,
                "Unfortunately, we do not have the sufficient blood quantity to finish " +
                        "the scheduled delivery. We sincerely apologize for the inconvenience");
    }
}
