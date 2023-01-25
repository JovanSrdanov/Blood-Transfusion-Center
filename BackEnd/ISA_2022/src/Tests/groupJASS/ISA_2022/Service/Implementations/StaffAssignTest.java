package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Service.Interfaces.IStaffService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
public class StaffAssignTest {
    @Autowired
    private IStaffService _staffService;

    private UUID staffId;
    private UUID centerId1;
    private UUID centerId2;

    @BeforeEach
    void setUp() {
        //Arrange
        staffId = UUID.fromString("e6dad8c3-1c14-4084-af7e-7aa653158cb2") ;
        centerId1 = UUID.fromString("417c9b36-251a-4483-bfbf-abd3df786d96") ;
        centerId2 = UUID.fromString("aa116b1b-c59c-403b-b64f-db201d9375d2") ;
    }

    @Test
    void assign_staff_two_times() throws Throwable {
        System.out.println("Waiting 7 seconds...");
        Thread.sleep(7000);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<?> thread1Result = executor.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("Thread 1: Started");
                try{
                    _staffService.assignBloodCenter(staffId, centerId1);
                    System.out.println("Thread 1: Staff assigned");
                } catch (Exception e) {
                    System.out.println("Thread 1: exc " + e.getMessage());
                }
            }
        });

        Future<?> thread2Result = executor.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("Thread 2: Started");
                try{
                    _staffService.assignBloodCenter(staffId, centerId2);
                    System.out.println("Thread 2: Staff assigned");
                } catch (Exception e) {
                    System.out.println("Thread 2: exc " + e.getMessage());
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

        Thread.sleep(40000);
    }
}
