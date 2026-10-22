import java.util.Scanner;

/*   
Commenter: William Xiong   

You should have separate files for objects, like a enemy object maybe?   
You should at least have .toLowerCase and .Strip for user input, and so it doesn't immediately stop if the user types in smth wrong   
or don't immediately js stop the code, maybe a while loop ig   
have user inputs be more intuitive, like when u ask engage or evade, say what they should type   
uh   
fuel can run out and game will continue.    

Commenter: Thor F   
Good things: The simulations are good  
Bad things: It doesnt have any try excepts or other railguards for user input. Also no instructions for how to play   

*/   

/**
 * Stores the jet's resources and runs the player's flight decisions and events.
 */
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

    private DeploymentLocation playerLocation;
    private boolean flightActive = true;


    // class constructor to initialize jet properties I like the all choices the user gets - Leo
    /**
     * Creates a jet with its starting resources and speed.
     * @param jetName the name of the jet
     * @param missileCount the starting number of missiles
     * @param bombCount the starting number of bombs
     * @param speed the maximum speed
     * @param fuelAmount the starting fuel amount
     */
    public jetsim(String jetName, int missileCount, int bombCount, int speed, int fuelAmount) {
        jet = jetName;
        numMissiles = missileCount;
        numBombs = bombCount;
        maxSpeed = speed;
        fuel = fuelAmount;
        playerLocation = null;

        maxMissiles = missileCount;
        maxBombs = bombCount;
        maxFuel = fuelAmount;

        enemiesDestroyed = 0;
    }


/**
 * Sets the player's current deployment location.
 * @param location the location selected by the player
 */
public void setPlayerLocation(DeploymentLocation location)
{
    this.playerLocation = location;
}
    // Getters
    /** @return the name of the jet */
    public String getJet() {
        return jet;
    }

    /** @return the current number of missiles */
    public int getNumMissiles() {
        return numMissiles;
    }

    /** @return the current number of bombs */
    public int getNumBombs() {
        return numBombs;
    }

    /** @return the maximum speed of the jet */
    public int getMaxSpeed() {
        return maxSpeed;
    }

    /** @return the current fuel amount */
    public int getFuel() {
        return fuel;
    }

    /** @return the current unbanked points */
    public int getPoints() {
        return points;
    }

    /** @return the points saved by returning to base */
    public int getBankedPoints() {
        return bankedPoints;
    }

    /** @return the number of enemies destroyed in the current mission */
    public int getEnemiesDestroyed() {
        return enemiesDestroyed;
    }

    /** @return true if the flight is still active */
    public boolean isFlightActive() {
        return flightActive;
    }

    // Setters
    /** @param missileCount the new number of missiles */
    public void setNumMissiles(int missileCount) {
        numMissiles = missileCount;
    }

    /** @param bombCount the new number of bombs */
    public void setNumBombs(int bombCount) {
        numBombs = bombCount;
    }

    /** @param fuelAmount the new fuel amount */
    public void setFuel(int fuelAmount) {
        fuel = fuelAmount;
    }

    /** @param pointTotal the new current point total */
    public void setPoints(int pointTotal) {
        points = pointTotal;
    }

    /** @param bankedPointTotal the new banked point total */
    public void setBankedPoints(int bankedPointTotal) {
        bankedPoints = bankedPointTotal;
    }

    /** @param enemyCount the new number of enemies destroyed */
    public void setEnemiesDestroyed(int enemyCount) {
        enemiesDestroyed = enemyCount;
    }

    /** @param active whether the flight should remain active */
    public void setFlightActive(boolean active) {
        flightActive = active;
    }

    /** Displays the jet's current information and deployment location. */
    public void displayInfo() {
    System.out.println("");
    System.out.println("Jet Name: " + getJet());
    System.out.println("Number of Missiles: " + getNumMissiles());
    System.out.println("Number of Bombs: " + getNumBombs());
    System.out.println("Max Speed: " + getMaxSpeed() + " km/h");
    System.out.println("Fuel: " + getFuel() + " liters");

    if (playerLocation != null) {
        System.out.println("Deployment Location: " + playerLocation.getName());
        System.out.println("Enemies Destroyed: " + getEnemiesDestroyed() + "/" + playerLocation.getWinQuota());
    }
}

    /** Displays the current and banked points. */
    public void displayPoints() {
        System.out.println("Current Points: " + getPoints());
        System.out.println("Banked Points: " + getBankedPoints());
    }

    // Randomly chooses an event
    /**
     * Chooses an event based on the selected deployment location.
     * Precondition: a deployment location has been selected.
     * Postcondition: an event is chosen and resources are checked if the flight remains active.
     * @param scanner reads the player's choices
     */
    public void randomEvent(Scanner scanner) {

        int eventChance = (int)(Math.random() * 100) + 1;

        if (eventChance <= playerLocation.getAircraftChance()) {
    enemyAircraft(scanner);
} else if (eventChance <= playerLocation.getGroundChance()) {
    enemyGround(scanner);
} else if (eventChance <= playerLocation.getUnknownAircraftChance()) {
    unknownAircraft(scanner);
} else {
    System.out.println("No events occurred during this part of the flight.");
    ;
}
if (isFlightActive()) {
    checkResources(scanner);
}
    }


    // Decides whether to continue flying or return to base
    /**
     * Lets the player continue flying or return to base.
     * @param scanner reads the player's choice
     */
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

    if (getFuel() <= 0) {
        System.out.println("You have run out of fuel and crashed before you could reach the airbase.");

        gameOver();
        return;
    }
  displayInfo();
  displayPoints();
    }
    else if ("return".equals(choice)) {

        returnToBase(scanner);
    }
}

    // Returns to base, banks points and has option to end game without risking points
    /**
     * Refills resources and banks points when the player returns to base.
     * @param scanner reads whether the player wants to continue or end
     */
    public void returnToBase(Scanner scanner) {

        if (getEnemiesDestroyed() >= playerLocation.getWinQuota()) {

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

    /** Ends the flight and displays the final information. */
    public void gameOver() {

        setFlightActive(false);

        System.out.println("");
        System.out.println("GAME OVER");

        displayInfo();

        System.out.println("Final Points: " + getBankedPoints());
    }

    // unknown aircraft with chance of being an enemy or ally
    /**
     * Lets the player investigate an unknown aircraft or continue flying.
     * @param scanner reads the player's choice
     */
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
    /**
     * Handles enemy ground targets and the player's choices.
     * @param scanner reads the player's choice
     */
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
    /**
     * Handles enemy aircraft and the player's choices.
     * @param scanner reads the player's choice
     */
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
                gameOver();
            }
        }
    }

    // Checks fuel and weapons and decide next action based on resources
    /**
     * Checks the player's resources and asks what to do next.
     * Precondition: a deployment location has been selected.
     * Postcondition: the player is prompted to continue or return, unless the flight has ended.
     * @param scanner reads the player's choice
     */
    public void checkResources(Scanner scanner) { // fuel is able to reach negative values and the game will continue until the user decides to return to base or crash - Leo
if (!isFlightActive()) {
    return;
}
        if (getEnemiesDestroyed() >= playerLocation.getWinQuota()) {

            System.out.println("You have destroyed enough enemies to complete the mission.");


        }
        if(getFuel() <=0){

            System.out.println("You have run out of fuel and crashed before you could reach the airbase.");

            gameOver();
            return;
        } 
        else if (getFuel() <= 500) {

            System.out.println("You are on the very last bit of fuel and MUST return to base.");


        }
        else if (getFuel() < 1000) {

            System.out.println("You are low on fuel and should return to base.");


        }
         if (getNumMissiles() <= 2 || getNumBombs() <= 2) {

            System.out.println("You are low on missiles or bombs and should return to base.");


        }


            flightDecision(scanner);

    }
}