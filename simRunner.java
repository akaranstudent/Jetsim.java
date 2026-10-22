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

DeploymentLocation coastalBase = new DeploymentLocation("Coastal Base", 15, 35, 60, 3);
DeploymentLocation desertBase = new DeploymentLocation("Desert Base", 10, 20, 45, 5);
DeploymentLocation mountainBase = new DeploymentLocation("Mountain Base", 20, 30, 40, 7);

// Object array created using new Type[size]
DeploymentLocation[] locations = new DeploymentLocation[3];
locations[0] = coastalBase;
locations[1] = desertBase;
locations[2] = mountainBase;

DeploymentLocation playerLocation;

// Primitive array created using new Type[size]
int[] missionQuotas = new int[locations.length];

// Enhanced for-each loop
int quotaIndex = 0;
for (DeploymentLocation location : locations) {
    missionQuotas[quotaIndex] = location.getWinQuota();
    quotaIndex++;
}

// Three array algorithms
// Three array algorithms
int locationsMeetingQuota =
    DeploymentLocation.countLocationsWithQuota(locations, 3);

DeploymentLocation foundLocation =
    DeploymentLocation.findLocationByName(locations, "Coastal Base");

double averageQuota =
    DeploymentLocation.calculateAverageQuota(locations);

// Use the results without changing the game's output
if (locationsMeetingQuota < 0 || averageQuota < 0
        || (foundLocation != null && foundLocation.getWinQuota() < 0)) {
    throw new IllegalStateException("Invalid deployment location data.");
}

// Your original indexed loop
for (int i = 0; i < locations.length; i++) {
    System.out.println(locations[i].toString());
}

String choice2 = scanner.nextLine().toLowerCase().trim();
while (!choice2.equals("coastal base") && !choice2.equals("desert base") && !choice2.equals("mountain base")) {
            System.out.println("Invalid location choice. Please type Coastal Base, Desert Base, or Mountain Base.");
            System.out.print("Please enter the name of a location to deploy to: ");
            choice2 = scanner.nextLine().toLowerCase().trim();
        }

        switch (choice2) {
            case "coastal base":
                playerLocation = locations[0];
                break;

            case "desert base":
                playerLocation = locations[1];
                break;

            case "mountain base":
                playerLocation = locations[2];
                break;

            default:
                return;
        }
       playerJet.setPlayerLocation(playerLocation);
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