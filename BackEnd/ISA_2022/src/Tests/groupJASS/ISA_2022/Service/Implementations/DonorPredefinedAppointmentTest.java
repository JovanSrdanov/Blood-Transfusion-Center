package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Questionnaire;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import groupJASS.ISA_2022.Service.Interfaces.IQuestionnaireService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.orm.jpa.JpaSystemException;
import org.webjars.NotFoundException;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
class DonorPredefinedAppointmentTest {
    @Autowired
    private IAppointmentService _appointmentService;
    @Autowired
    private IQuestionnaireService _questionnaireService;

    private UUID firstDonorId;
    private UUID secondDonorId;
    private UUID firstAppointmentId;
    private UUID secondAppointmentId;

    @BeforeEach
    void setUp() throws BadRequestException {
        firstDonorId = UUID.fromString("07ce2e8b-d34b-4156-9dd4-f29ec4311675");
        secondDonorId = UUID.fromString("26d3381b-319d-425c-abd7-256f24f0a2e0");
        firstAppointmentId = UUID.fromString("dbf02dce-a9a1-4e20-aaf0-5fca92299407");

        Questionnaire firstQuestionnaire = new Questionnaire(
                UUID.randomUUID(), false, false, false,
                false, false, false, false,
                false
        );

        Questionnaire secondQuestionnaire = new Questionnaire(
                UUID.randomUUID(), false, false, false,
                false, false, false, false,
                false
        );

        _questionnaireService.fillQuestionnaire(firstQuestionnaire, firstDonorId);
        _questionnaireService.fillQuestionnaire(secondQuestionnaire, secondDonorId);
    }

    @Test
    void second_schedule_request_fails() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            ExecutorService executor = Executors.newFixedThreadPool(2);

            Future<?> thread1Result = executor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("Thread 1: Started");
                    try {
                        _appointmentService.scheduleAppointment(firstDonorId, firstAppointmentId);
                        System.out.println("Thread 1: Appointment scheduled");
                    }
                    catch (JpaSystemException | CannotAcquireLockException e) {
                        System.out.println("Thread 1: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.scheduleAppointment(firstDonorId, firstAppointmentId);
                    }
                }
            });

            Future<?> thread2Result = executor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("Thread 2: Started");

                    try {
                        _appointmentService.scheduleAppointment(secondDonorId, firstAppointmentId);
                        System.out.println("Thread 2: Appointment scheduled");
                    }
                    catch (JpaSystemException | CannotAcquireLockException e) {
                        System.out.println("Thread 2: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.scheduleAppointment(secondDonorId, firstAppointmentId);
                        System.out.println("Thread 2: Appointment scheduled");
                    }
                }
            });

            try {
                thread1Result.get();
                thread2Result.get();
            }
            catch (ExecutionException e) {
                System.out.println("Exception from thread " + e.getCause().getClass());
                System.out.println("Message: " + e.getCause().getMessage());
                throw e.getCause();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            executor.shutdown();
        });
    }

    @Test
    void
}