package ExternalHospital.ExternalHospital.DeliveryContract;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @RabbitListener(queues = "deliveryResponse")
    public void receiveResponse(String message) {
        System.out.println("Consumer queue: " + "deliveryResponse " + "activated");
        System.out.println("Message: " + message);
    }
}
