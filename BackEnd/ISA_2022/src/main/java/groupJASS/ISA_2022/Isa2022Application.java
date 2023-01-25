package groupJASS.ISA_2022;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Blood Bank", version = "0.1", description = "Projekat za ISU 22/23"))
public class Isa2022Application {

    @Value("${demandBloodShipment}")
    String demandBloodShipment;
    @Value("${approvedBloodShipment}")
    String approvedBloodShipment;
    @Value("${bloodShipmentArrived}")
    String bloodShipmentArrived;
    @Value("${setGPSCoordinates}")
    String setGPSCoordinates;
    @Value("${getCurrentGPSCoordinates}")
    String getCurrentGPSCoordinates;
    @Value("${helicopterArrived}")
    String helicopterArrived;

    @Value("${letterbox}")
    String letterbox;

    @Value("${letterbox2}")
    String letterbox2;

    @Value("${createContract}")
    String createContract;

    @Value("${deliveryResponse}")
    String deliveryResponse;


    public static void main(String[] args) {
        SpringApplication.run(Isa2022Application.class, args);
        System.out.println("Welcome!");
    }

    @Bean
    Queue demandBloodShipmentQueue() {
        return new Queue(demandBloodShipment, true);
    }

    @Bean
    Queue approvedBloodShipmentQueue() {
        return new Queue(approvedBloodShipment, true);
    }

    @Bean
    Queue bloodShipmentArrivedQueue() {
        return new Queue(bloodShipmentArrived, true);
    }

    @Bean
    Queue setGPSCoordinatesQueue() {
        return new Queue(setGPSCoordinates, true);
    }

    @Bean
    Queue getCurrentGPSCoordinatesQueue() {
        return new Queue(getCurrentGPSCoordinates, true);
    }

    @Bean
    Queue helicopterArrivedQueue() {
        return new Queue(helicopterArrived, true);
    }

    @Bean
    Queue letterboxQueue() {
        return new Queue(letterbox, true);
    }

    @Bean
    Queue letterbox2Queue() {
        return new Queue(letterbox2, true);
    }

    @Bean
    Queue deliveryResponseQueue() {
        return new Queue(deliveryResponse, true);
    }

    @Bean
    Queue createContractQueue() {
        return new Queue(createContract, true);
    }


    /*@Bean
    public ObjectMapperUtils modelMapper2() {
        return new ObjectMapperUtils();
    }*/

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure().buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        return connectionFactory;
    }


}

