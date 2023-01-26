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

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
class AppointmentServiceTest {

    //Constructor injection doesn't work for some reason
    @Autowired
    private   IAppointmentService _appointmentService;
    @Autowired
    private IQuestionnaireService _questionnaireService;

    private UUID donorId;
    private UUID staffId;

    @BeforeEach
    void setUp() throws BadRequestException {
        //Arrange
         donorId = UUID.fromString("e23e75a8-6815-4931-92e0-6611c8ee3d21") ;
         staffId = UUID.fromString("4b3d3597-5de9-4890-9d46-93fa3c1a746a") ;

        Questionnaire questionnaire = new Questionnaire(UUID.randomUUID(), false, false, false, false, false, false, false, false);
        _questionnaireService.fillQuestionnaire(questionnaire, donorId);
    }

    @Test
    void second_schedule_request_fails() throws Throwable {
        Assertions.assertThrows(BadRequestException.class, () -> {

            LocalDateTime time1 = LocalDateTime.of(2023, 2, 16, 10, 0);
            LocalDateTime time2 = LocalDateTime.of(2023, 2, 16, 10, 0);

            ExecutorService executor = Executors.newFixedThreadPool(2);

            Future<?> thread1Result = executor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("Thread 1: Started");
                    try{
                        _appointmentService.scheduleCustomAppointment(donorId, time1, staffId);
                        System.out.println("Thread 1: Appointment scheduled");
                    }
                    catch (JpaSystemException | CannotAcquireLockException e)
                    {
                        System.out.println("Thread 1: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.scheduleCustomAppointment(donorId, time1, staffId);
                        System.out.println("Thread 1: Appointment scheduled");
                    }
                    catch (RuntimeException e)
                    {
                        System.out.println("Thread 1: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.scheduleCustomAppointment(donorId, time2, staffId);
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
                        _appointmentService.scheduleCustomAppointment(donorId, time2, staffId);
                        System.out.println("Thread 2: Appointment scheduled");
                    }
                    catch (JpaSystemException | CannotAcquireLockException e)
                    {
                        System.out.println("Thread 2: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.scheduleCustomAppointment(donorId, time2, staffId);
                        System.out.println("Thread 2: Appointment scheduled");
                    }
                    catch (RuntimeException e)
                    {
                        System.out.println("Thread 2: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.scheduleCustomAppointment(donorId, time2, staffId);
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
        });
    }




    @Test
    void second_schedule_request_fails_first_time_then_succeeds() throws Throwable {
            LocalDateTime time1 = LocalDateTime.of(2023, 2, 17, 10, 0);
            LocalDateTime time2 = LocalDateTime.of(2023, 2, 17, 11, 0);


            ExecutorService executor = Executors.newFixedThreadPool(2);

            Future<?> thread1Result = executor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("Thread 1: Started");
                    try{
                        _appointmentService.scheduleCustomAppointment(donorId, time1, staffId);
                        System.out.println("Thread 1: Appointment scheduled");
                    }
                    catch (JpaSystemException | CannotAcquireLockException e)
                    {
                        System.out.println("Thread 1: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.scheduleCustomAppointment(donorId, time1, staffId);
                        System.out.println("Thread 1: Appointment scheduled");
                    }
                    catch (RuntimeException e)
                    {
                        System.out.println("Thread 1: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.scheduleCustomAppointment(donorId, time2, staffId);
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
                        _appointmentService.scheduleCustomAppointment(donorId, time2, staffId);
                        System.out.println("Thread 2: Appointment scheduled");
                    }
                    catch (JpaSystemException | CannotAcquireLockException e)
                    {
                        System.out.println("Thread 2: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.scheduleCustomAppointment(donorId, time2, staffId);
                        System.out.println("Thread 2: Appointment scheduled");
                    }
                    catch (RuntimeException e)
                    {
                        System.out.println("Thread 2: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.scheduleCustomAppointment(donorId, time2, staffId);
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