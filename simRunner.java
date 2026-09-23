import java.util.Scanner;

public class simRunner {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        jetsim a10 = new jetsim("A-10", 4, 8, 706, 5020);
        jetsim f16 = new jetsim("F-16", 6, 4, 2120, 3160);
        jetsim f18 = new jetsim("F/A-18", 5, 5, 1915, 6200);

        System.out.println("Welcome to the Jet Simulator! To play, you will choose a jet and engage in various missions. Your goal is to destroy enemy targets and earn points. Be careful, as running out of fuel can lead to mission failure while running out of weapons puts you at higher risk. Returning to base refuels/rearms and secures your points permamently but returning without meeting quota causes you to lose points. Enter your choices as seen on the screen");

        System.out.println("Please enter the name of a jet to fly:");

      
        jetsim[] jets = {a10, f16, f18};

        for (int i = 0; i < jets.length; i++) {
            jets[i].displayInfo();
            System.out.println("");
        }

        String choice = scanner.nextLine().toLowerCase().trim();

        // Input validation while loop
        while (!choice.equals("a-10") && !choice.equals("f-16") && !choice.equals("f/a-18")) {
            System.out.println("Invalid jet choice. Please type A-10, F-16, or F/A-18.");
            System.out.print("Please enter the name of a jet to fly: ");
            choice = scanner.nextLine().toLowerCase().trim();
        }

        jetsim playerJet;

        switch (choice) {
            case "a-10":
                playerJet = a10;
                break;

            case "f-16":
                playerJet = f16;
                break;

            case "f/a-18":
                playerJet = f18;
                break;

            default:
                return;
        }

        playerJet.displayInfo();

        // Dynamic game loop controlled by the flightActive boolean
        while (playerJet.isFlightActive()) {

            playerJet.randomEvent(scanner);

            // Conditional inside the loop changes whether the loop continues
            if (!playerJet.isFlightActive()) {
                break;
            }
        }

        scanner.close();
    }
}