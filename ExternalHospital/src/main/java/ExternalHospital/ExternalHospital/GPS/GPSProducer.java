package ExternalHospital.ExternalHospital.GPS;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
        this.rabbitTemplate.convertAndSend(this.demandBloodShipment, demandBloodShipmentDTO);
        System.out.println("Message sent\n");
    }

}
