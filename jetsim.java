import java.util.Scanner
public class jetsim {
    String jet;
 int numMissiles;
 int numBombs;
    int maxSpeed;
    int fuel;
    
    public jetsim(String n, int m, int b, int speed, int f) {
        jet = n;
        numMissiles = m;
        numBombs = b;
        maxSpeed = speed;
        fuel = f;
    }


public void displayInfo() {
        System.out.println("Jet Name: " + jet);
        System.out.println("Number of Missiles: " + numMissiles);
        System.out.println("Number of Bombs: " + numBombs);
        System.out.println("Max Speed: " + maxSpeed + " km/h");
        System.out.println("Fuel Capacity: " + fuel + " liters");
}

public static void randomEvent(){
   int event = (int)(Math.random() * 100)+1; // Generates a random number between 1 and 100
    if(event<=10){
      playerJet.enemyAircraft();
    }
    else if(event<=25){
      playerJet.unknownAircraft();
    }
    else{
      System.out.print("No events occurred during this part of the flight.");
    }
}

public void unknownAircraft(){
    Scanner scanner = new Scanner(System.in);
    System.out.print("Unknown aircraft spotted. Will you investigate or continue on your flight?");
  if("investigate".equals(scanner.nextLine()) && (Math.random()*2+1) == 1){
    System.out.print("You identify it as an enemy aircraft.");
    playerJet.enemyAircraft();
    
  }
  else{
    System.out.print("You identify it as an ally and continue on your flight.");
  }
}
public void enemyAircraft(){
    System.out.print("Enemy aircraft spotted. Will you engage or evade?");
  Scanner scanner = new Scanner(System.in);
  if("engage".equals(scanner.nextLine())){
    System.out.print("You engage the enemy aircraft successfully.");
       if(numMissiles>0){
           numMissiles--;
       }
       else if(numMissiles==0 && (Math.random()*2+1) == 1){
           System.out.print("You win in a dogfight.");
       }
       else{
            System.out.print("You lose in a dogfight.");
       }
  }
  else if("evade".equals(scanner.nextLine()) && (Math.random()*2+1) == 2){
    System.out.print("You evade the enemy aircraft.");
  }
}
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    jetsim playerJet = new jetsim("F-22 Raptor", 6, 2, 2410, 8200);
    playerJet.displayInfo();
    randomEvent();
    
    unknownAircraft();
}




























}
