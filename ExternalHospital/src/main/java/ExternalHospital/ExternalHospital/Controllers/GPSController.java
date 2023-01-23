package ExternalHospital.ExternalHospital.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gps")
public class GPSController {


    @GetMapping("test")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>("GPS CONTROLLER WORKS", HttpStatus.OK);
    }
}
