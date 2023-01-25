package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PredefineAndScheduleCustomTest {
    //Constructor injection doesn't work for some reason
    @Autowired
    private IAppointmentService _appointmentService;
}
