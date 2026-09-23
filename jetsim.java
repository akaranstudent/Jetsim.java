import java.util.Scanner;

public class jetsim {

    private String jet;
    private int numMissiles;
    private int numBombs;
    private int maxSpeed;
    private int fuel;

    private int maxMissiles;
    private int maxBombs;
    private int maxFuel;

    private int points;
    private int bankedPoints;

    private int enemiesDestroyed;
    private int missionTarget = 3;

    private boolean flightActive = true;

    public jetsim(String jetName, int missileCount, int bombCount, int speed, int fuelAmount) {
        jet = jetName;
        numMissiles = missileCount;
        numBombs = bombCount;
        maxSpeed = speed;
        fuel = fuelAmount;

        maxMissiles = missileCount;
        maxBombs = bombCount;
        maxFuel = fuelAmount;

        enemiesDestroyed = 0;
    }

    // overloaded constructor
    public jetsim(String jetName, int missileCount, int bombCount, int speed) {
        this(jetName, missileCount, bombCount, speed, 5000);
    }

    // Getters
    public String getJet() {
        return jet;
    }

    public int getNumMissiles() {
        return numMissiles;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getFuel() {
        return fuel;
    }

    public int getPoints() {
        return points;
    }

    public int getBankedPoints() {
        return bankedPoints;
    }

    public int getEnemiesDestroyed() {
        return enemiesDestroyed;
    }

    public boolean isFlightActive() {
        return flightActive;
    }

    // Setters
    public void setNumMissiles(int missileCount) {
        numMissiles = missileCount;
    }

    public void setNumBombs(int bombCount) {
        numBombs = bombCount;
    }

    public void setFuel(int fuelAmount) {
        fuel = fuelAmount;
    }

    public void setPoints(int pointTotal) {
        points = pointTotal;
    }

    public void setBankedPoints(int bankedPointTotal) {
        bankedPoints = bankedPointTotal;
    }

    public void setEnemiesDestroyed(int enemyCount) {
        enemiesDestroyed = enemyCount;
    }

    public void setFlightActive(boolean active) {
        flightActive = active;
    }

    public void displayInfo() {
        System.out.println("");
        System.out.println("Jet Name: " + getJet());
        System.out.println("Number of Missiles: " + getNumMissiles());
        System.out.println("Number of Bombs: " + getNumBombs());
        System.out.println("Max Speed: " + getMaxSpeed() + " km/h");
        System.out.println("Fuel: " + getFuel() + " liters");
        System.out.println("Enemies Destroyed: " + getEnemiesDestroyed() + "/" + missionTarget);
    }

    public void displayPoints() {
        System.out.println("Current Points: " + getPoints());
        System.out.println("Banked Points: " + getBankedPoints());
    }

    // Randomly chooses an event
    public void randomEvent(Scanner scanner) {

        int eventChance = (int)(Math.random() * 100) + 1;

        if (eventChance <= 10) {
            enemyAircraft(scanner);
        }
        else if (eventChance <= 25) {
            unknownAircraft(scanner);
        }
        else if (eventChance <= 50) {
            enemyGround(scanner);
        }
        else {
            System.out.println("No events occurred during this part of the flight.");
        }

        checkResources(scanner);
    }

    // Decides whether to continue flying or return to base
    public void flightDecision(Scanner scanner) {

        String choice = "";

        while (!choice.equals("continue") && !choice.equals("return")) {

            System.out.print("Do you want to continue flying or return to base? Type continue or return: ");

            choice = scanner.nextLine().toLowerCase().trim();

            if (!choice.equals("continue") && !choice.equals("return")) {
                System.out.println("Invalid choice. Please type continue or return.");
            }
        }

        if ("continue".equals(choice)) {

            setFuel(getFuel() - 500);

            displayInfo();
            displayPoints();
        }
        else if ("return".equals(choice)) {

            returnToBase(scanner);
        }
    }

    // Returns to base, banks points and has option to end game without risking points
    public void returnToBase(Scanner scanner) {

        if (getEnemiesDestroyed() >= missionTarget) {

            setBankedPoints(getBankedPoints() + getPoints());

            System.out.println("Mission complete!");
            System.out.println("All points have been secured.");
        }
        else {

            setPoints(getPoints() - 500);

            if (getPoints() < 0) {
                setPoints(0);
            }

            setBankedPoints(getBankedPoints() + getPoints());

            System.out.println("Mission incomplete.");
            System.out.println("You lost 500 points for not meeting the quota.");
        }

        setPoints(0);

        setFuel(maxFuel);
        setNumMissiles(maxMissiles);
        setNumBombs(maxBombs);

        setEnemiesDestroyed(0);

        System.out.println("You have returned to base.");
        System.out.println("Your fuel and weapons have been refilled.");
        System.out.println("Points banked: " + getBankedPoints());

        displayInfo();

        String choice = "";

        while (!choice.equals("end") && !choice.equals("continue")) {

            System.out.print("Do you want to end the flight or continue? Type end or continue: ");

            choice = scanner.nextLine().toLowerCase().trim();

            if (!choice.equals("end") && !choice.equals("continue")) {
                System.out.println("Invalid choice. Please type end or continue.");
            }
        }

        if ("continue".equals(choice)) {

            System.out.println("You take off again.");

            setFlightActive(true);
        }
        else if ("end".equals(choice)) {

            gameOver();
        }
    }

    public void gameOver() {

        setFlightActive(false);

        System.out.println("");
        System.out.println("GAME OVER");

        displayInfo();

        System.out.println("Final Points: " + getBankedPoints());
    }

    // unknown aircraft with chance of being an enemy or ally
    public void unknownAircraft(Scanner scanner) {

        String choice = "";

        while (!choice.equals("investigate") && !choice.equals("continue")) {

            System.out.print("Unknown aircraft spotted. Will you investigate or continue on your flight? Type investigate or continue: ");

            choice = scanner.nextLine().toLowerCase().trim();

            if (!choice.equals("investigate") && !choice.equals("continue")) {
                System.out.println("Invalid choice. Please type investigate or continue.");
            }
        }

        if ("investigate".equals(choice)) {

            int result = (int)(Math.random() * 2) + 1;

            if (result == 1) {

                System.out.println("You identify it as an enemy aircraft.");

                enemyAircraft(scanner);
            }
            else {

                setPoints(getPoints() + 50);

                System.out.println("You identify it as an ally.");
                System.out.println("You earned 50 points.");
            }
        }
        else if ("continue".equals(choice)) {

            System.out.println("You continue on your flight.");
        }
    }

    // enemy ground targets with normal forces and anti-air forces with chance of being destroyed or shooting down the player
    public void enemyGround(Scanner scanner) {

        int targetType = (int)(Math.random() * 4) + 1;

        if (targetType <= 3) {

            System.out.println("Enemy ground installation spotted.");

            String choice = "";

            while (!choice.equals("engage") && !choice.equals("continue")) {

                System.out.print("Will you engage or continue? Type engage or continue: ");

                choice = scanner.nextLine().toLowerCase().trim();

                if (!choice.equals("engage") && !choice.equals("continue")) {
                    System.out.println("Invalid choice. Please type engage or continue.");
                }
            }

            if ("engage".equals(choice)) {

                if (getNumBombs() > 0) {

                    setNumBombs(getNumBombs() - 1);
                    setEnemiesDestroyed(getEnemiesDestroyed() + 1);
                    setPoints(getPoints() + 100);

                    System.out.println("You destroy the enemy ground installation.");
                    System.out.println("You earned 100 points.");
                }
                else {

                    System.out.println("You have no bombs left.");
                }
            }
            else if ("continue".equals(choice)) {

                System.out.println("You continue on your flight.");
            }
        }
        else {

            System.out.println("Enemy anti-air installation spotted.");

            String choice = "";

            while (!choice.equals("engage") && !choice.equals("evade")) {

                System.out.print("Will you engage or evade? Type engage or evade: ");

                choice = scanner.nextLine().toLowerCase().trim();

                if (!choice.equals("engage") && !choice.equals("evade")) {
                    System.out.println("Invalid choice. Please type engage or evade.");
                }
            }

            if ("engage".equals(choice)) {

                if (getNumBombs() > 0) {

                    setNumBombs(getNumBombs() - 1);

                    int survivalChance = (int)(Math.random() * 100) + 1;

                    if (survivalChance <= 15) {

                        System.out.println("The anti-air installation shoots you down.");

                        gameOver();
                    }
                    else {

                        setEnemiesDestroyed(getEnemiesDestroyed() + 1);
                        setPoints(getPoints() + 175);

                        System.out.println("You destroy the anti-air installation.");
                        System.out.println("You earned 175 points.");
                    }
                }
                else {

                    int survivalChance = (int)(Math.random() * 100) + 1;

                    if (survivalChance <= 50) {

                        System.out.println("The anti-air installation shoots you down.");

                        gameOver();
                    }
                    else {

                        System.out.println("You have no bombs, but you survive the anti-air fire.");
                    }
                }
            }
            else if ("evade".equals(choice)) {

                System.out.println("You evade the anti-air installation.");
            }
        }
    }

    // enemy aircraft that you can shoot or evade with chance of winning or losing in a dogfight
    public void enemyAircraft(Scanner scanner) {

        String choice = "";

        while (!choice.equals("engage") && !choice.equals("evade")) {

            System.out.print("Enemy aircraft spotted. Will you engage or evade? Type engage or evade: ");

            choice = scanner.nextLine().toLowerCase().trim();

            if (!choice.equals("engage") && !choice.equals("evade")) {
                System.out.println("Invalid choice. Please type engage or evade.");
            }
        }

        if ("engage".equals(choice)) {

            if (getNumMissiles() > 0) {

                setNumMissiles(getNumMissiles() - 1);
                setEnemiesDestroyed(getEnemiesDestroyed() + 1);
                setPoints(getPoints() + 100);

                System.out.println("You engage the enemy aircraft successfully.");
                System.out.println("You earned 100 points.");
            }
            else {

                int dogfightResult = (int)(Math.random() * 2) + 1;

                if (dogfightResult == 1) {

                    setEnemiesDestroyed(getEnemiesDestroyed() + 1);
                    setPoints(getPoints() + 200);

                    System.out.println("You win in a dogfight.");
                    System.out.println("You earned 200 points.");
                }
                else {

                    System.out.println("You lose in a dogfight.");

                    gameOver();
                }
            }
        }
        else if ("evade".equals(choice)) {

            int enemySpeed = (int)(Math.random() * 1301) + 1500;

            if (getMaxSpeed() > enemySpeed) {

                System.out.println("You successfully evade the enemy aircraft.");
            }
            else {

                System.out.println("You are unable to evade the enemy aircraft.");
            }
        }
    }

    // Checks fuel and weapons and decide next action based on resources
    public void checkResources(Scanner scanner) {

        if (getEnemiesDestroyed() >= missionTarget) {

            System.out.println("You have destroyed enough enemies to complete the mission.");

            flightDecision(scanner);
        }
        else if (getFuel() <= 500) {

            System.out.println("You have run out of fuel and crashed before you could reach the airbase.");

            gameOver();
        }
        else if (getFuel() < 1000) {

            System.out.println("You are low on fuel and should return to base.");

            flightDecision(scanner);
        }
        else if (getNumMissiles() <= 2 || getNumBombs() <= 2) {

            System.out.println("You are low on missiles or bombs and should return to base.");

            flightDecision(scanner);
        }
        else {

            flightDecision(scanner);
        }
    }
}