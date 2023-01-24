package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Questionnaire;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import groupJASS.ISA_2022.Service.Interfaces.IQuestionnaireService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNull;

//@ExtendWith(SpringRunner.class)
@SpringBootTest
class AppointmentServiceTest {

    //Constructor injection doesn't work for some reason
    @Autowired
    private   IAppointmentService _appoitmentService;
    @Autowired
    private IQuestionnaireService _questionnaireService;


    @Test
    void second_schedule_request_fails() throws Throwable {
        Assertions.assertThrows(BadRequestException.class, () -> {
            //Arrange
            UUID donorId = UUID.fromString("07ce2e8b-d34b-4156-9dd4-f29ec4311675") ;
            UUID staffId = UUID.fromString("8180fea1-7623-4a5b-8717-5b34b2abe9d3") ;
            LocalDateTime time1 = LocalDateTime.of(2023, 1, 27, 10, 0);
            LocalDateTime time2 = LocalDateTime.of(2023, 1, 27, 10, 0);

            Questionnaire questionnaire = new Questionnaire(UUID.randomUUID(), false, false, false, false, false, false, false, false);
            _questionnaireService.fillQuestionnaire(questionnaire, donorId);

            //Act
            ExecutorService executor = Executors.newFixedThreadPool(2);

            Future<?> thread1Result = executor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    _appoitmentService.scheduleCustomAppointment(donorId, time1, staffId);
                    System.out.println("Thread 1: Pregled zakazan");
                }
            });

            Future<?> thread2Result = executor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    _appoitmentService.scheduleCustomAppointment(donorId, time2, staffId);
                    System.out.println("Thread 2: Pregled zakazan");
                }
            });

            try{
                thread2Result.get();
            }
            catch (ExecutionException e)
            {
                System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas PessimisticLockingFailureException
                System.out.println("Message: " + e.getCause().getMessage()); // u pitanju je bas PessimisticLockingFailureException
                throw e.getCause();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executor.shutdown();

        });
    }
}