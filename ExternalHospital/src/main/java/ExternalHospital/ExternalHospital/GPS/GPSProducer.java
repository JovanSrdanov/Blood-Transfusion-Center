package ExternalHospital.ExternalHospital.GPS;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GPSProducer {
    @Value("${demandBloodShipment}")
    String demandBloodShipment;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void demandBloodShipment(DemandBloodShipmentDTO demandBloodShipmentDTO) {
        System.out.println("Producer: " + this.demandBloodShipment + " activated");

        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(DemandBloodShipmentDTO.class);
        jsonConverter.setClassMapper(classMapper);
        rabbitTemplate.setMessageConverter(jsonConverter);

        this.rabbitTemplate.convertAndSend(this.demandBloodShipment, demandBloodShipmentDTO);
        System.out.println("Message sent\n");
    }

}
