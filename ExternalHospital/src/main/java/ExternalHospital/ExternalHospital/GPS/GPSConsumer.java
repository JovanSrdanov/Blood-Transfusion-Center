package ExternalHospital.ExternalHospital.GPS;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class GPSConsumer {
    @RabbitListener(queues = "${approvedBloodShipment}")
    public void approvedBloodShipment(String message) {
        System.out.println("Consumer: " + "approvedBloodShipment" + " activated");
        System.out.println("Message: " + message + "\n");
    }

    @RabbitListener(queues = "${bloodShipmentArrived}")
    public void bloodShipmentArrived(String message) {
        System.out.println("Consumer: " + "bloodShipmentArrived" + " activated");
        System.out.println("Message: " + message + "\n");
    }
}
