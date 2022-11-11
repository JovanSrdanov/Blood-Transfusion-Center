package groupJASS.ISA_2022.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ControllerTest {

    @GetMapping("test")
    public String Cao() {
        return "CAO";
    }

    @GetMapping("test2")
    public String Cao2() {
        return "CAO2";
    }
}
