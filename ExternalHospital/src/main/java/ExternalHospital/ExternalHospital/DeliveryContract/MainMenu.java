package ExternalHospital.ExternalHospital.DeliveryContract;

import ExternalHospital.ExternalHospital.Model.BloodGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
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

        if(input.equals("2")) return;

        createNewContract();
    }

    private void createNewContract() {
        try {
            Scanner sc = new Scanner(System.in);
            int bloodGroupInput;
            BloodGroup bloodGroup;

            do {
                System.out.println("Choose a blood group:\n1)A-Positive 2)A-Negative 3)B-Positive " +
                        "4)B-Negative\n5)O-Positive 6)O-Negative 7)AB-Positive 8)AB-Negative");

                bloodGroupInput = sc.nextInt();
            }
            while(bloodGroupInput > 8 || bloodGroupInput < 1);

            switch (bloodGroupInput) {
                case 1 -> bloodGroup = BloodGroup.A_POS;
                case 2 -> bloodGroup = BloodGroup.A_NEG;
                case 3 -> bloodGroup = BloodGroup.B_POS;
                case 4 -> bloodGroup = BloodGroup.B_NEG;
                case 5 -> bloodGroup = BloodGroup.O_POS;
                case 6 -> bloodGroup = BloodGroup.O_NEG;
                case 7 -> bloodGroup = BloodGroup.AB_POS;
                case 8 -> bloodGroup = BloodGroup.AB_NEG;
                default -> bloodGroup = BloodGroup.A_POS;
            }

            System.out.println("\nChoose desired blood quantity (in integers): ");
            int quantity = sc.nextInt();

            int deliveryDay = 0;

            do {
                System.out.println("Please enter the desired day of the delivery which is at least " +
                        "two days after today\n" +
                        "(the delivery will happen on said day every month):\n");

                sc.nextLine();

                deliveryDay = sc.nextInt();
            }
            while(deliveryDay - LocalDate.now().getDayOfMonth() <= 2 &&
                    deliveryDay >= LocalDate.now().getDayOfMonth());

            ContractDTO contract = new ContractDTO(
                    UUID.fromString(hospitalId), bloodGroup, quantity, deliveryDay, "externalHospital"
            );

            _producer.createContract(contract);

            System.out.println("\n*********************");
            System.out.println("Delivery contract successfully created! Contract details:\n");
            System.out.println("Delivery day: " + deliveryDay + "\n");
            System.out.println("Blood group: " + bloodGroup + ", Quantity: " + quantity);

            System.out.println("\n\nNote that only one contract may be active at a time and" +
                    " creating a new contract will replace the existing one");
            System.out.println("\n*********************");
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid quantity/day of the month.");
            startMainMenu();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            startMainMenu();
        }
    }
}
