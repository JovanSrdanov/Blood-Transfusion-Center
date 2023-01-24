package ExternalHospital.ExternalHospital.DeliveryContract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

@Component
public class MainMenu implements CommandLineRunner {
    @Value("${hospitalId}")
    private String hospitalId;
    @Autowired
    private ContractProducer _producer;

    @Override
    public void run(String... args) throws Exception {
        startMainMenu();
    }

    public void startMainMenu() {
        String input;
        do {
            System.out.println("\nPress 1 to create a new contract");
            System.out.println("Press 2 to proceed\n");

            Scanner sc = new Scanner(System.in);
            input = sc.nextLine();
        }
        while (!(input.equals("1") || input.equals("2")));

        if (input.equals("1")) {
            createNewContract();
        }
    }

    private void createNewContract() {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Please enter the desired quantity for each blood group\n");
            System.out.println("A-Positive: ");
            int aPos = sc.nextInt();
            System.out.println("A-Negative: ");
            int aNeg = sc.nextInt();
            System.out.println("B-Positive: ");
            int bPos = sc.nextInt();
            System.out.println("B-Negative: ");
            int bNeg = sc.nextInt();
            System.out.println("O-Positive: ");
            int oPos = sc.nextInt();
            System.out.println("O-Negative: ");
            int oNeg = sc.nextInt();
            System.out.println("AB-Positive: ");
            int abPos = sc.nextInt();
            System.out.println("AB-Negative: ");
            int abNeg = sc.nextInt();

            System.out.println("Please enter the desired date (format is dd-MM-yyyy) of the delivery " +
                    "(the delivery will happen on said date every month):\n");

            sc.nextLine();

            String date = sc.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date delivery = sdf.parse(date);

            ContractDTO contract = new ContractDTO(
                    UUID.fromString(hospitalId), aPos, aNeg, bPos, bNeg, oPos, oNeg, abPos, abNeg, delivery
            );

            _producer.createContract(contract);

            System.out.println("\n*********************");
            System.out.println("Delivery contract successfully created! Contract details:\n");
            System.out.println("Delivery date: " + delivery + "\n");
            System.out.println("Blood quantities per blood group: ");
            System.out.println("A-Positive: " + aPos + ", A-Negative: " + aNeg + ", B-Positive: "  + bPos
                    + ", B-Negative: " + bNeg + ", O-Positive: " + oPos + ", O-Negative: " + oNeg
                    + ", AB-Positive: " + abPos + ", AB-Negative: " + abNeg);

            System.out.println("\n\nNote that only one contract can be active at a time and" +
                    " creating a new one will replace the existing contract if one already exists");
            System.out.println("\n*********************");
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid quantity.");
            startMainMenu();
        }
        catch (ParseException e) {
            System.out.println("Invalid input! Please enter a valid date.");
            startMainMenu();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
