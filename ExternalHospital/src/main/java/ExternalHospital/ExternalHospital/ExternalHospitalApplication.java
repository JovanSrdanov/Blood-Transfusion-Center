package ExternalHospital.ExternalHospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class ExternalHospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExternalHospitalApplication.class, args);
		System.out.println("--- EXTERNAL HOSPITAL ---");
	}

}
