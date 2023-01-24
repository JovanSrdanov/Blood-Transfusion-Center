package groupJASS.ISA_2022;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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

    public static void main(String[] args) {
        SpringApplication.run(Isa2022Application.class, args);
        System.out.println("IDEMOOOOOOOOOOOOOOOOOOOOOOOO");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /*@Bean
    public ObjectMapperUtils modelMapper2() {
        return new ObjectMapperUtils();
    }*/

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

