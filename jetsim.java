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

    int enemiesDestroyed;
    int missionTarget = 3;

    public jetsim(String n, int m, int b, int speed, int f) { // class constructor to initialize jet properties I like the all choices the user gets - Leo
        jet = n;
        numMissiles = m;
        numBombs = b;
        maxSpeed = speed;
        fuel = f;

        maxMissiles = m;
        maxBombs = b;
        maxFuel = f;

        enemiesDestroyed = 0;
    }

    public void displayInfo() {
        System.out.println("");
        System.out.println("Jet Name: " + jet);
        System.out.println("Number of Missiles: " + numMissiles);
        System.out.println("Number of Bombs: " + numBombs);
        System.out.println("Max Speed: " + maxSpeed + " km/h");
        System.out.println("Fuel: " + fuel + " liters");
        System.out.println("Enemies Destroyed: " + enemiesDestroyed + "/" + missionTarget);
        System.out.println("Current Points: " + points);
        System.out.println("Banked Points: " + bankedPoints);
    }

    // Randomly chooses an event
    public void randomEvent() {
        int event = (int)(Math.random() * 100) + 1;
System.out.println();
        if (event <= 10) {
            enemyAircraft();
        }
        else if (event <= 25) {
            unknownAircraft();
        }
        else if (event <= 50) {
            enemyGround();
        }
        else {
            System.out.println("No events occurred during this part of the flight.");
        }

        checkResources();
    }

    // Decides whether to continue flying or return to base
    public void flightDecision() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to continue flying or return to base? ");
        String choice = scanner.nextLine();

        if ("continue".equals(choice)) {
            fuel -= 500;
            displayInfo();
            randomEvent();
        }
        else if ("return".equals(choice)) {
            returnToBase();
        }
    }

    // Returns to base, banks points and has option to end game without risking points
    public void returnToBase() {
        Scanner scanner = new Scanner(System.in);

        if (enemiesDestroyed >= missionTarget) {
            bankedPoints += points;
            System.out.println("Mission complete!");
            System.out.println("All points have been secured.");
        }
        else {
            points -= 500;

            if (points < 0) {
                points = 0;
            }

            bankedPoints += points;

            System.out.println("Mission incomplete.");
            System.out.println("You lost 500 points for not meeting the quota.");
        }

        points = 0;

        fuel = maxFuel;
        numMissiles = maxMissiles;
        numBombs = maxBombs;

        enemiesDestroyed = 0;

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
        System.out.println("");
        System.out.println("GAME OVER");
        displayInfo();
        System.out.println("Final Points: " + bankedPoints);
    }

    // unknown aircraft with chance of being an enemy or ally 
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
                points += 50;
                System.out.println("You identify it as an ally.");
                System.out.println("You earned 50 points.");
            }
        }
        else if ("continue".equals(choice)) {
            System.out.println("You continue on your flight.");
        }
    }

    // enemy ground targets with normal forces and anti-air forces with chance of being destroyed or shooting down the player
    public void enemyGround() {
        Scanner scanner = new Scanner(System.in);

        int target = (int)(Math.random() * 4) + 1;

        if (target <= 3) {
            System.out.println("Enemy ground installation spotted.");
            System.out.print("Will you engage or continue? ");
            String choice = scanner.nextLine();

            if ("engage".equals(choice)) {

                if (numBombs > 0) {
                    numBombs--;
                    enemiesDestroyed++;
                    points += 100;

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
            System.out.print("Will you engage or evade? ");
            String choice = scanner.nextLine();

            if ("engage".equals(choice)) {

                if (numBombs > 0) {
                    numBombs--;

                    int result = (int)(Math.random() * 100) + 1;

                    if (result <= 15) {
                        System.out.println("The anti-air installation shoots you down.");
                        gameOver();
                    }
                    else {
                        enemiesDestroyed++;
                        points += 175;

                        System.out.println("You destroy the anti-air installation.");
                        System.out.println("You earned 175 points.");
                    }
                }
                else {
                    int result = (int)(Math.random() * 100) + 1;

                    if (result <= 50) {
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
    public void enemyAircraft() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enemy aircraft spotted. Will you engage or evade? ");
        String choice = scanner.nextLine();

        if ("engage".equals(choice)) {

            if (numMissiles > 0) {
                numMissiles--;
                enemiesDestroyed++;
                points += 100;

                System.out.println("You engage the enemy aircraft successfully.");
                System.out.println("You earned 100 points.");
            }
            else {
                int result = (int)(Math.random() * 2) + 1;

                if (result == 1) {
                    enemiesDestroyed++;
                    points += 200;

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

            if (maxSpeed > enemySpeed) {
                System.out.println("You successfully evade the enemy aircraft.");
            }
            else {
                System.out.println("You are unable to evade the enemy aircraft.");
            }
        }
    }

    // Checks fuel and weapons and decide next action based on resources
    public void checkResources() { // fuel is able to reach negative values and the game will continue until the user decides to return to base or crash - Leo

        if (fuel <= 500) {
            System.out.println("You have run out of fuel and crashed before you could reach the airbase.");
            gameOver();
        }
        else if (fuel < 1000) {
            System.out.println("You are low on fuel and should return to base.");
            flightDecision();
        }
        else if (numMissiles <= 2 || numBombs <= 2) {
            System.out.println("You are low on missiles or bombs and should return to base.");
            flightDecision();
        }
        else {
            flightDecision();
        }
    }

    public static void main(String[] args) { // try using a while loop to keep the game going until the user decides to quit maybe there could be a while loop with breaks to meet requirements - Leo

        Scanner scanner = new Scanner(System.in);

        jetsim a10 = new jetsim("A-10", 4, 8, 706, 5020);
        jetsim f16 = new jetsim("F-16", 6, 4, 2120, 3160);
        jetsim f18 = new jetsim("F/A-18", 5, 5, 1915, 6200);

        System.out.println("Welcome to the Jet Simulator!");
        System.out.println("Please enter the name of a jet to fly:");
        System.out.println("A-10");
        a10.displayInfo();
       System.out.println("");
        System.out.println("F-16");
        f16.displayInfo();
       System.out.println("");
        System.out.println("F/A-18");
        f18.displayInfo();
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
        playerJet.randomEvent(); // This function adds a lot of unexpected events to the game and makes it really fun - Leo
    }
}