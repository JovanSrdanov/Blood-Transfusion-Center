package ExternalHospital.ExternalHospital.GPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gps")
public class GPSController {
    @Value("${server.port}")
    String serverPort;
    @Autowired
    private GPSProducer _GPSProducer;

    @PostMapping("demandBloodShipment")
    public ResponseEntity<?> demandBloodShipment(@RequestBody DemandBloodShipmentDTO demandBloodShipmentDTO) {
        demandBloodShipmentDTO.setServerPort(serverPort);
        _GPSProducer.demandBloodShipment(demandBloodShipmentDTO);
        return new ResponseEntity<>("Blood shipment demand sent", HttpStatus.OK);
    }
}
