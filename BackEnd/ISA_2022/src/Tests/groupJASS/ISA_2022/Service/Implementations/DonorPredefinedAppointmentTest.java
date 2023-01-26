package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Questionnaire;
import groupJASS.ISA_2022.Service.Interfaces.IAppointmentService;
import groupJASS.ISA_2022.Service.Interfaces.IQuestionnaireService;
import lombok.SneakyThrows;
import net.bytebuddy.implementation.bytecode.Throw;
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

    @BeforeEach//002c3ba4-3c55-421d-9222-b473dd4af087   d29d8157-bc1c-4723-84cc-1743a224099b
    void setUp() throws BadRequestException {
        firstDonorId = UUID.fromString("e23e75a8-6815-4931-92e0-6611c8ee3d21");
        secondDonorId = UUID.fromString("00b898e5-36a8-4b2e-a3b9-e6bc86c24c8f");
        firstAppointmentId = UUID.fromString("dd2bdc29-9cd1-46eb-8dce-efb8ae7d6e57");
        secondAppointmentId = UUID.fromString("812db0cc-f899-405e-bb0c-66a1d1658616");

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
    void second_schedule_request_fails_first_time_then_succeeds() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<?> thread1Result = executor.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("Thread 1: Started");
                try{
                    _appointmentService.scheduleAppointment(secondDonorId, firstAppointmentId);
                    System.out.println("Thread 1: Appointment scheduled");
                }
                catch (JpaSystemException |  CannotAcquireLockException e)
                {
                    System.out.println("Thread 1: resource locked, waiting 2 seconds...");
                    Thread.sleep(2000);
                    _appointmentService.scheduleAppointment(secondDonorId, firstAppointmentId);
                    System.out.println("Thread 1: Appointment scheduled");
                }
            }
        });

        Future<?> thread2Result = executor.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("Thread 2: Started");
                try{
                    _appointmentService.scheduleAppointment(secondDonorId, secondAppointmentId);
                    System.out.println("Thread 2: Appointment scheduled");
                }
                catch (JpaSystemException | CannotAcquireLockException e)
                {
                    System.out.println("Thread 2: resource locked, waiting 2 seconds...");
                    Thread.sleep(2000);
                    _appointmentService.scheduleAppointment(secondDonorId, secondAppointmentId);
                    System.out.println("Thread 2: Appointment scheduled");
                }
            }
        });

        try{
            thread1Result.get();
            thread2Result.get();
        }
        catch (ExecutionException e)
        {
            System.out.println("Exception from thread " + e.getCause().getClass());
            System.out.println("Message: " + e.getCause().getMessage());
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}