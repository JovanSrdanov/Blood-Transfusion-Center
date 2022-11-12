package groupJASS.ISA_2022;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Isa2022Application {

    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(Isa2022Application.class, args);
        System.out.println("Pozdrav svima!");
    }

}

