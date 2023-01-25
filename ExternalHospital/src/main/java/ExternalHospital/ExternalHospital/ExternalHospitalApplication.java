package ExternalHospital.ExternalHospital;
import ExternalHospital.ExternalHospital.DeliveryContract.ContractDTO;
import ExternalHospital.ExternalHospital.DeliveryContract.ContractProducer;
import ExternalHospital.ExternalHospital.DeliveryContract.MainMenu;
import ExternalHospital.ExternalHospital.GPS.DemandBloodShipmentDTO;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;

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
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;


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

	//region Delivery contract
	@Value("${createContract}")
	String createContractQueue;

	@Value("${deliveryResponse}")
	String deliveryResponseQueue;

	@Value("${externalHospital}")
	String externalHospital;
	@Bean
	Queue createContractQueue() { return new Queue(createContractQueue, true); }

	@Bean
	Queue deliveryResponseQueue() { return new Queue(deliveryResponseQueue, true); }

	@Bean
	Queue externalHospitalQueue() {
		return new Queue(externalHospital, true);
	}
	//endregion

	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory("localhost");
	}
}
