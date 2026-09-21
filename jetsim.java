import java.util.Scanner;

public class jetsim {

    String jet;
    int numMissiles;
    int numBombs;
    int maxSpeed;
    int fuel;

    int maxMissiles;
    int maxBombs;
    int maxFuel;

    int points;
    int bankedPoints;

    public jetsim(String n, int m, int b, int speed, int f) {
        jet = n;
        numMissiles = m;
        numBombs = b;
        maxSpeed = speed;
        fuel = f;

        maxMissiles = m;
        maxBombs = b;
        maxFuel = f;
    }

    public void displayInfo() {
        System.out.println("Jet Name: " + jet);
        System.out.println("Number of Missiles: " + numMissiles);
        System.out.println("Number of Bombs: " + numBombs);
        System.out.println("Max Speed: " + maxSpeed + " km/h");
        System.out.println("Fuel Capacity: " + fuel + " liters");
    }

    public void randomEvent() {
        int event = (int)(Math.random() * 100) + 1;

        if (event <= 10) {
            enemyAircraft();
        }
        else if (event <= 25) {
            unknownAircraft();
        }
        else {
            System.out.println("No events occurred during this part of the flight.");
        }

        flightDecision();
    }

    public void flightDecision() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to continue flying or return to base? ");
        String choice = scanner.nextLine();

        if ("continue".equals(choice)) {
            fuel -= 500;
            displayInfo();
            checkResources();
            randomEvent();
        }
        else if ("return".equals(choice)) {
            returnToBase();
        }
    }

    public void returnToBase() {
        Scanner scanner = new Scanner(System.in);

        fuel = maxFuel;
        numMissiles = maxMissiles;
        numBombs = maxBombs;

        bankedPoints += points;
        points = 0;

        System.out.println("You have returned to base.");
        System.out.println("Your fuel and weapons have been refilled.");
        System.out.println("Points banked: " + bankedPoints);

        displayInfo();

        System.out.print("Do you want to end the flight or continue? ");
        String choice = scanner.nextLine();

        if ("continue".equals(choice)) {
            System.out.println("You take off again.");
            randomEvent();
        }
        else if ("end".equals(choice)) {
            gameOver();
        }
    }

    public void gameOver() {
        System.out.println("GAME OVER");
        displayInfo();
        System.out.println("Final Points: " + bankedPoints);
    }

    public void unknownAircraft() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Unknown aircraft spotted. Will you investigate or continue on your flight? ");
        String choice = scanner.nextLine();

        if ("investigate".equals(choice)) {

            int result = (int)(Math.random() * 2) + 1;

            if (result == 1) {
                System.out.println("You identify it as an enemy aircraft.");
                enemyAircraft();
            }
            else {
                System.out.println("You identify it as an ally and continue on your flight.");
            }
        }
        else if ("continue".equals(choice)) {
            System.out.println("You continue on your flight.");
        }
    }

    public void enemyAircraft() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enemy aircraft spotted. Will you engage or evade? ");
        String choice = scanner.nextLine();

        if ("engage".equals(choice)) {

            if (numMissiles > 0) {
                numMissiles--;
                System.out.println("You engage the enemy aircraft successfully.");
            }
            else {
                int result = (int)(Math.random() * 2) + 1;

                if (result == 1) {
                    System.out.println("You win in a dogfight.");
                }
                else {
                    System.out.println("You lose in a dogfight.");
                }
            }
        }
        else if ("evade".equals(choice)) {

            int enemySpeed = (int)(Math.random() * 1301) + 1500;

            if (maxSpeed > enemySpeed) {
                System.out.println("You successfully evade the enemy aircraft.");
            }
            else {
                System.out.println("You are unable to evade the enemy aircraft.");
            }
        }
    }

    public void checkResources() {

        if (fuel <= 0) {
            System.out.println("You have run out of fuel and crashed.");
            gameOver();
        }
        else if (fuel < 500) {
            System.out.println("You are low on fuel and should return to base.");
            flightDecision();
        }
        else if (numMissiles <= 2 || numBombs <= 2) {
            System.out.println("You are low on missiles or bombs and should return to base.");
            flightDecision();
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        jetsim a10 = new jetsim("A-10", 4, 8, 706, 5020);
        jetsim f16 = new jetsim("F-16", 6, 4, 2120, 3160);
        jetsim f18 = new jetsim("F/A-18", 5, 5, 1915, 6200);

        System.out.println("Welcome to the Jet Simulator!");
        System.out.println("Please enter the name of a jet to fly:");
        System.out.println("A-10");
        System.out.println("F-16");
        System.out.println("F/A-18");

        String choice = scanner.nextLine();

        jetsim playerJet;

        switch (choice) {
            case "A-10":
                playerJet = a10;
                break;

            case "F-16":
                playerJet = f16;
                break;

            case "F/A-18":
                playerJet = f18;
                break;

            default:
                return;
        }

        playerJet.displayInfo();
        playerJet.randomEvent();
    }
}