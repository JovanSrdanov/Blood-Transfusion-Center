package ExternalHospital.ExternalHospital;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableAsync
@EnableScheduling
public class ExternalHospitalApplication {

	@Value("${myexchange}")
	String exchange;
	@Value("${routingkey}")
	String routingkey;
	@Value("${demandBloodShipment}")
	String demandBloodShipment;
	@Value("${approvedBloodShipment}")
	String approvedBloodShipment;
	@Value("${bloodShipmentArrived}")
	String bloodShipmentArrived;

	public static void main(String[] args) {
		SpringApplication.run(ExternalHospitalApplication.class, args);
		System.out.println("--- EXTERNAL HOSPITAL ---");
	}


	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
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
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		return connectionFactory;
	}

}
