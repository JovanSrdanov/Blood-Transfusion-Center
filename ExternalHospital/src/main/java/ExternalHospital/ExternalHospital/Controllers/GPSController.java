package ExternalHospital.ExternalHospital.Controllers;

import ExternalHospital.ExternalHospital.Utilities.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gps")
public class GPSController {
    @Autowired
    private Producer producer;



    @GetMapping("test")
    public ResponseEntity<?> findAll() {
        producer.sendTo("spring-boot1", "cao stefane");
        return new ResponseEntity<>("GPS CONTROLLER WORKS", HttpStatus.OK);
    }
}
