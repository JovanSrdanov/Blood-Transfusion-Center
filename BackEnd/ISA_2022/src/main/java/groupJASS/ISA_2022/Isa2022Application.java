package groupJASS.ISA_2022;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootApplication
public class Isa2022Application {

    public static void main(String[] args) {
        SpringApplication.run(Isa2022Application.class, args);
        System.out.println("Pozdrav svima!");
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

