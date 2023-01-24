package HelicopterGPS.HelicopterGPS;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class ConsumerHelicopterGPS {

    @RabbitListener(queues = "${setGPSCoordinates}")
    public void receiveBloodShippmentDemand(Message message) throws IOException {
        System.out.println("Consumer: " + "setGPSCoordinates" + "activated");
        byte[] body = message.getBody();
        ObjectMapper mapper = new ObjectMapper();
        CoordinatesForGPSDTO coordinatesForGPSDTO = mapper.readValue(body, CoordinatesForGPSDTO.class);
        System.out.println(coordinatesForGPSDTO);


    }
}
