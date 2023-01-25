package HelicopterGPS.HelicopterGPS;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.lang.Math.max;

@Service
public class PathForHelicopterService implements IPathForHelicopterService{

    private final RabbitTemplate _rabbitTemplate;
    private final ModelMapper _mapper;
    private final PathForHelicopterRepository _pathForHelicopterRepository;
    @Value("${getCurrentGPSCoordinates}")
    String getCurrentGPSCoordinates;
    @Value("${helicopterArrived}")
    String helicopterArrived;

    @Autowired
    public PathForHelicopterService(PathForHelicopterRepository pathForHelicopterRepository,RabbitTemplate rabbitTemplate,ModelMapper mapper){
        _pathForHelicopterRepository= pathForHelicopterRepository;
        _rabbitTemplate=rabbitTemplate;
        _mapper= mapper;
    }
    @Override
    public PathForHelicopter findNotDelivered() {
       return _pathForHelicopterRepository.findUndelivered();
    }

    @Override
    public PathForHelicopter save(PathForHelicopter entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        return _pathForHelicopterRepository.save(entity);
    }


    @Scheduled(cron = "${checkInterval}")
    public void checkIfThereAreUndelivered() throws InterruptedException {
     System.out.println("Checking if there are undelivered blood supplies");

        PathForHelicopter pathForHelicopter= findNotDelivered();
        if(pathForHelicopter==null){
        return;
        }
        System.out.println("There are undelivered blood supplies");
        calculateCoordinates(pathForHelicopter);

    }

    private void calculateCoordinates(PathForHelicopter pathForHelicopter) throws InterruptedException {

        double distance = haversine(pathForHelicopter.getCurLatitude(), pathForHelicopter.getCurLongitude(), pathForHelicopter.getDestLatitude(), pathForHelicopter.getDestLongitude());

        double increment = pathForHelicopter.getSeconds();
        int steps = (int) (distance  / increment); // convert distance to meters and divide by increment
        double latIncrement = (pathForHelicopter.getDestLatitude() - pathForHelicopter.getCurLatitude()) / steps;
        double lonIncrement = (pathForHelicopter.getDestLongitude() - pathForHelicopter.getCurLongitude()) / steps;

       while(true){
            pathForHelicopter.setCurLatitude( pathForHelicopter.getCurLatitude() + ( latIncrement));
            pathForHelicopter.setCurLongitude(pathForHelicopter.getCurLongitude() + ( lonIncrement));
            save(pathForHelicopter);
            System.out.println(pathForHelicopter.getCurLatitude() +" "+ pathForHelicopter.getCurLongitude());

           CurrentHelicopterPositionDTO currentHelicopterPositionDTO = _mapper.map(pathForHelicopter, CurrentHelicopterPositionDTO.class);
            produceCoordinates(currentHelicopterPositionDTO);
            
            if(haversine(pathForHelicopter.getCurLatitude(), pathForHelicopter.getCurLongitude(), pathForHelicopter.getDestLatitude(), pathForHelicopter.getDestLongitude())<max(latIncrement,lonIncrement)*0.01){
                System.out.println("Helicopter has arrived");
                pathForHelicopter.setDelivered(true);
                save(pathForHelicopter);
                produceArrivalMessage(currentHelicopterPositionDTO);
                break;
            }
            Thread.sleep(pathForHelicopter.getSeconds()* 1000L);
        }
    }

    private void produceArrivalMessage(CurrentHelicopterPositionDTO currentHelicopterPositionDTO) {
        System.out.println("Sending arrival message...");
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(CurrentHelicopterPositionDTO.class);
        jsonConverter.setClassMapper(classMapper);
        _rabbitTemplate.setMessageConverter(jsonConverter);
        this._rabbitTemplate.convertAndSend(this.helicopterArrived, currentHelicopterPositionDTO);
        System.out.println("Message sent\n");
    }

    private void produceCoordinates(CurrentHelicopterPositionDTO currentHelicopterPositionDTO) {
        System.out.println("Sending coordinates...");
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(CurrentHelicopterPositionDTO.class);
        jsonConverter.setClassMapper(classMapper);
        _rabbitTemplate.setMessageConverter(jsonConverter);
        this._rabbitTemplate.convertAndSend(this.getCurrentGPSCoordinates, currentHelicopterPositionDTO);
        System.out.println("Message sent\n");
    }

    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6372.8; //
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

}
