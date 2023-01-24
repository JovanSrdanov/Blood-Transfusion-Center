package HelicopterGPS.HelicopterGPS;

import HelicopterGPS.HelicopterGPS.Utilities.ObjectMapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class ConsumerHelicopterGPS {
    private final ModelMapper _mapper;
    private IPathForHelicopterService _pathForHelicopterService;
    @Autowired
    public ConsumerHelicopterGPS(IPathForHelicopterService pathForHelicopterService, ModelMapper mapper){
        _pathForHelicopterService=pathForHelicopterService;
        _mapper = mapper;
    }

    @RabbitListener(queues = "${setGPSCoordinates}")
    public void receiveBloodShippmentDemand(Message message) throws IOException {
       try {
           System.out.println("Consumer: " + "setGPSCoordinates" + "activated");
           byte[] body = message.getBody();
           ObjectMapper mapper = new ObjectMapper();
           CoordinatesForGPSDTO coordinatesForGPSDTO = mapper.readValue(body, CoordinatesForGPSDTO.class);
           System.out.println(coordinatesForGPSDTO);

           PathForHelicopter pathForHelicopter= _mapper.map(coordinatesForGPSDTO, PathForHelicopter.class);
           pathForHelicopter.setCurLatitude(pathForHelicopter.getSrcLatitude());
           pathForHelicopter.setCurLongitude(pathForHelicopter.getSrcLongitude());
           pathForHelicopter.setDelivered(false);

           _pathForHelicopterService.save(pathForHelicopter);

       }
       catch (Exception e){
           System.out.println(e.getMessage());
       }

    }
}
