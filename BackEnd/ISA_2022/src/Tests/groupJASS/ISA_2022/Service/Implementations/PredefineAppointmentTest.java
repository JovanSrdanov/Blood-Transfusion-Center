package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.DateRange;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
public class PredefineAppointmentTest {
    //Constructor injection doesn't work for some reason
    @Autowired
    private IAppointmentService _appointmentService;

    @Autowired
    private IQuestionnaireService _questionnaireService;

    private DateRange dateRange;
    private List<UUID> staffIds;
    private UUID adminId1;
    private UUID adminId2;

    private UUID donorId;
    private boolean isPredef;

    @BeforeEach
    void setUp() throws BadRequestException {
        adminId1 = UUID.fromString("32763cf0-6a56-49f1-ad25-5a90561cd204"); //Doc1
        adminId2 = UUID.fromString("883f13cc-25c8-4b60-b0e5-5ac938ad2594"); //Doc2

        donorId = UUID.fromString("07ce2e8b-d34b-4156-9dd4-f29ec4311675"); //Donor

        staffIds = new ArrayList<>();
        staffIds.add(adminId1);

        isPredef = true;

        Questionnaire questionnaire = new Questionnaire(UUID.randomUUID(), false, false, false, false, false, false, false, false);
        _questionnaireService.fillQuestionnaire(questionnaire, donorId);
    }

    @Test
    void second_predefine_request_fails() throws Throwable {
        LocalDateTime startTime = LocalDateTime.of(2023, 1, 30, 8, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 1, 30, 8, 20);
        dateRange = new DateRange(startTime,endTime);

        Assertions.assertThrows(BadRequestException.class, () -> {
            ExecutorService executor = Executors.newFixedThreadPool(2);

            Future<?> thread1Result = executor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("Thread 1: Started");
                    try{
                        _appointmentService.predefine(dateRange, staffIds, adminId1, isPredef);
                        System.out.println("Thread 1: Appointment scheduled");
                    }
                    catch (JpaSystemException | CannotAcquireLockException e)
                    {
                        System.out.println("Thread 1: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.predefine(dateRange, staffIds, adminId1, isPredef);
                    }
                }
            });

            Future<?> thread2Result = executor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("Thread 2: Started");
                    try{
                        _appointmentService.predefine(dateRange, staffIds, adminId2, isPredef);
                        System.out.println("Thread 2: Appointment scheduled");
                    }
                    catch (JpaSystemException | CannotAcquireLockException e)
                    {
                        System.out.println("Thread 2: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.predefine(dateRange, staffIds, adminId2, isPredef);
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

        //Da moze da se vidi u bazi
        Thread.sleep(40000);
    }

    @Test
    void custom_and_predefine_custom_fails() throws Throwable {
        LocalDateTime startTime = LocalDateTime.of(2023, 2, 4, 8, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 2, 4, 8, 20);
        dateRange = new DateRange(startTime,endTime);

        Assertions.assertThrows(BadRequestException.class, () -> {
            ExecutorService executor = Executors.newFixedThreadPool(2);

            Future<?> thread1Result = executor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("Thread 1: Started");
                    try{
                        _appointmentService.predefine(dateRange, staffIds, adminId1, isPredef);
                        System.out.println("Thread 1: Appointment scheduled");
                    }
                    catch (JpaSystemException | CannotAcquireLockException e)
                    {
                        System.out.println("Thread 1: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.predefine(dateRange, staffIds, adminId1, isPredef);
                    }
                }
            });

            Future<?> thread2Result = executor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("Thread 2: Started");
                    try{
                        _appointmentService.scheduleCustomAppointment(donorId, dateRange.getStartTime(), adminId1);
                        System.out.println("Thread 2: Appointment scheduled");
                    }
                    catch (JpaSystemException | CannotAcquireLockException e)
                    {
                        System.out.println("Thread 2: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.scheduleCustomAppointment(donorId, dateRange.getStartTime(), adminId1);
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

        //Da moze da se vidi u bazi
        Thread.sleep(40000);
    }

    @Test
    void custom_and_predefine_predefining_fails() throws Throwable {
        LocalDateTime startTime = LocalDateTime.of(2023, 2, 5, 8, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 2, 5, 8, 20);
        dateRange = new DateRange(startTime,endTime);

        System.out.println("Waiting 5 seconds...");
        //Thread.sleep(5000);

        Assertions.assertThrows(BadRequestException.class, () -> {
            ExecutorService executor = Executors.newFixedThreadPool(1);

            Future<?> thread1Result = executor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("Thread 1: Started");
                    try{
                        _appointmentService.scheduleCustomAppointment(donorId, dateRange.getStartTime(), adminId1);
                        System.out.println("Thread 1: Appointment scheduled");
                    }
                    catch (JpaSystemException | CannotAcquireLockException e)
                    {
                        System.out.println("Thread 1: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.scheduleCustomAppointment(donorId, dateRange.getStartTime(), adminId1);
                    }
                }
            });

            Future<?> thread2Result = executor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("Thread 2: Started");
                    try{
                        _appointmentService.predefine(dateRange, staffIds, adminId1, isPredef);
                        System.out.println("Thread 2: Appointment scheduled");
                    }
                    catch (JpaSystemException | CannotAcquireLockException e)
                    {
                        System.out.println("Thread 2: resource locked, waiting 2 seconds...");
                        Thread.sleep(2000);
                        _appointmentService.predefine(dateRange, staffIds, adminId1, isPredef);
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

        //Da moze da se vidi u bazi
        Thread.sleep(40000);
    }
}