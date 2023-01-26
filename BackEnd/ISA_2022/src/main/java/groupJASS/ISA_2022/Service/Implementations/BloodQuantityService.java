package groupJASS.ISA_2022.Service.Implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import groupJASS.ISA_2022.DTO.Blood.RabbitTestDto;
import groupJASS.ISA_2022.Exceptions.CenterOutOfBloodException;
import groupJASS.ISA_2022.Model.BloodQuantity;
import groupJASS.ISA_2022.Repository.BloodQuantityRepository;
import groupJASS.ISA_2022.Service.Interfaces.IBloodQuantityService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
public class BloodQuantityService implements IBloodQuantityService {
    private final BloodQuantityRepository _bloodQuantityRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${letterbox}")
    String letterbox;

    @Autowired
    public BloodQuantityService(BloodQuantityRepository bloodQuantityRepository) {
        this._bloodQuantityRepository = bloodQuantityRepository;
    }

    @Override
    public Iterable<BloodQuantity> findAll() {
        return _bloodQuantityRepository.findAll();
    }

    @Override
    public BloodQuantity findById(UUID id) {
        if (_bloodQuantityRepository.findById(id).isPresent()) {
            return _bloodQuantityRepository.findById(id).get();
        }
        throw new NotFoundException("Blood not found");
    }

    @Override
    public BloodQuantity save(BloodQuantity entity) {
        BloodQuantity quantity;
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
            quantity = _bloodQuantityRepository.save(entity);
        }
        else {
            BloodQuantity oldQuantity = findById(entity.getId());
            if (oldQuantity == null) {
                throw new NotFoundException("Blood not found");
            }
            oldQuantity.update(entity);
            quantity = _bloodQuantityRepository.save(oldQuantity);
        }

        return quantity;
    }

    @Override
    public void deleteById(UUID id) {
        _bloodQuantityRepository.deleteById(id);
    }


    @Override
    public BloodQuantity updateQuantity(UUID bloodId, int quantity) throws CenterOutOfBloodException {
        BloodQuantity updatedQantity = findById(bloodId);
        if (updatedQantity == null) {
            throw new NotFoundException("Blood not found");
        }

        updatedQantity.setQuantity(updatedQantity.getQuantity() + quantity);

        return save(updatedQantity);
    }

    @RabbitListener(queues = "${letterbox}")
    public void rabbitTest(Message message) {
        try {
            //System.out.println("Consumer: " + "demandBloodShipment" + " activated");
            byte[] body = message.getBody();
            ObjectMapper mapper = new ObjectMapper();
            RabbitTestDto dto = mapper.readValue(body, RabbitTestDto.class);

            System.out.println("Poruka: " + dto.getName());

            /*Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
            DefaultClassMapper classMapper = new DefaultClassMapper();
            classMapper.setDefaultType(RabbitTestDto.class);
            jsonConverter.setClassMapper(classMapper);
            rabbitTemplate.setMessageConverter(jsonConverter);

            this.rabbitTemplate.convertAndSend("letterbox2", new RabbitTestDto("Poruka sa isa strane"));
            System.out.println("Poruka poslata:");*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
