package groupJASS.ISA_2022;

import org.modelmapper.ModelMapper;
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

}

