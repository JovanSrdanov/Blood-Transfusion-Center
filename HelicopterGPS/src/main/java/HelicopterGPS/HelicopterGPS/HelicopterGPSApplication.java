package HelicopterGPS.HelicopterGPS;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableAsync
@EnableScheduling
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class HelicopterGPSApplication {
	@Value("${setGPSCoordinates}")
	String setGPSCoordinates;
	@Value("${getCurrentGPSCoordinates}")
	String getCurrentGPSCoordinates;
	@Value("${helicopterArrived}")
	String helicopterArrived;

	public static void main(String[] args) {
		SpringApplication.run(HelicopterGPSApplication.class, args);
		System.out.println("HELICOPTER GPS");
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
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		return connectionFactory;
	}


}
