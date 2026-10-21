public class DeploymentLocation {
private String name;
private int aircraftChance;
private int groundChance;
private int unknownAircraftChance;
private int winQuota;

    public DeploymentLocation(String name, int aircraftChance, int groundChance, int unknownAircraftChance, int winQuota) {
        this.name = name;
        this.aircraftChance = aircraftChance;
        this.groundChance = groundChance;
        this.unknownAircraftChance = unknownAircraftChance;
        this.winQuota = winQuota;
    }

    public String getName() {
        return name;
    }

    public int getAircraftChance() {
        return aircraftChance;
    }

    public int getGroundChance() {
        return groundChance;
    }

    public int getUnknownAircraftChance() {
        return unknownAircraftChance;
    }

    public int getWinQuota() {
        return winQuota;
    }
}
