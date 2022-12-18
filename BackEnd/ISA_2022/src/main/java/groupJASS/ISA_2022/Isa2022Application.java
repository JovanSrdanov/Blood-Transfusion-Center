package groupJASS.ISA_2022;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootApplication
@EnableAsync
public class Isa2022Application {

    public static void main(String[] args) {
        SpringApplication.run(Isa2022Application.class, args);
        System.out.println("Pozdrav svima!");
        System.out.println("Zelim svima ugodan dan, puno ljubavi, zdravlja i srece!");
        System.out.println("Tim moj najjaci <3");
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

