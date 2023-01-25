 package ExternalHospital.ExternalHospital.DeliveryContract;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ContractProducer {
    @Value("${createContract}")
    String createContractQueue;
    @Autowired
    private RabbitTemplate _template;

    public void createContract(ContractDTO newContract) {
        System.out.println("Contract producer queue: " + this.createContractQueue + " activated");

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper mapper = new DefaultClassMapper();
        mapper.setDefaultType(newContract.getClass());
        converter.setClassMapper(mapper);

        _template.setMessageConverter(converter);
        _template.convertAndSend(this.createContractQueue, newContract);

        System.out.println("Contract created");
    }
}
