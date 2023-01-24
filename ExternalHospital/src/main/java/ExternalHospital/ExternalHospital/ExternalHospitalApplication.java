package ExternalHospital.ExternalHospital;
import ExternalHospital.ExternalHospital.DeliveryContract.ContractDTO;
import ExternalHospital.ExternalHospital.DeliveryContract.ContractProducer;
import ExternalHospital.ExternalHospital.DeliveryContract.MainMenu;
import ExternalHospital.ExternalHospital.GPS.DemandBloodShipmentDTO;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
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
	public static void main(String[] args) {
		SpringApplication.run(ExternalHospitalApplication.class, args);

		System.out.println("--- EXTERNAL HOSPITAL ---");
	}

	@Value("${myqueue}")
	String queue;

	@Value("${myqueue2}")
	String queue2;

	@Value("${myexchange}")
	String exchange;

	@Value("${routingkey}")
	String routingkey;

	@Bean
	Queue queue() {
		return new Queue(queue, true);
	}

	@Bean
	Queue queue2() {
		return new Queue(queue2, true);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(Queue queue2, DirectExchange exchange) {
		return BindingBuilder.bind(queue2).to(exchange).with(routingkey);
	}

	//region Delivery contract
	@Value("${createContract}")
	String createContractQueue;

	@Value("${deliveryResponse}")
	String deliveryResponseQueue;
	@Bean
	Queue createContractQueue() { return new Queue(createContractQueue, true); }

	@Bean
	Queue deliveryResponseQueue() { return new Queue(deliveryResponseQueue, true); }
	//endregion

	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory("localhost");
	}

}
