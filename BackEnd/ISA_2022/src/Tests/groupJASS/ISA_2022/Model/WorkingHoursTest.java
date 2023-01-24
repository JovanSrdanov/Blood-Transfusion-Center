package groupJASS.ISA_2022.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkingHoursTest {

    @Test
    void creates_valid_working_hours() {
        WorkingHours workingHours = new WorkingHours(8,0,16,30);

        boolean result = workingHours.isValid();

        assertTrue(result);
    }

    @Test void creates_invalid_working_hours(){
        WorkingHours workingHours = new WorkingHours(8,70,50,30);

        boolean result = workingHours.isValid();

        assertFalse(result);
    }
}