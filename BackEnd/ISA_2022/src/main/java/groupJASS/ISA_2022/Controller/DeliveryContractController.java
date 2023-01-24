package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.Service.Interfaces.IDeliveryContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delivery-contract")
public class DeliveryContractController {
    private final IDeliveryContractService _deliveryContractService;

    @Autowired
    public DeliveryContractController(IDeliveryContractService deliveryContractService) {
        this._deliveryContractService = deliveryContractService;
    }
}
