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
    public String toString() {
        return "Deployment Location: " + name + "\n" +
               "Aircraft Chance: " + aircraftChance + "%\n" +
               "Ground Chance: " + groundChance + "%\n" +
               "Unknown Aircraft Chance: " + unknownAircraftChance + "%\n" +
               "Win Quota: " + winQuota;
    }

    /**
     * Counts locations whose mission quota meets a minimum.
     * Precondition: locations may be null or contain null entries.
     * Postcondition: returns the number of locations meeting the minimum.
     */
    public static int countLocationsWithQuota(
            DeploymentLocation[] locations, int minimumQuota) {
        int count = 0;

        if (locations == null) {
            return 0;
        }

        for (DeploymentLocation location : locations) {
            if (location != null && location.getWinQuota() >= minimumQuota) {
                count++;
            }
        }

        return count;
    }

    /**
     * Finds a location by name.
     * Precondition: locations may be null or contain null entries.
     * Postcondition: returns the matching location or null if not found.
     */
    public static DeploymentLocation findLocationByName(
            DeploymentLocation[] locations, String targetName) {
        if (locations == null || targetName == null) {
            return null;
        }

        for (DeploymentLocation location : locations) {
            if (location != null &&
                location.getName().equalsIgnoreCase(targetName.trim())) {
                return location;
            }
        }

        return null;
    }

    /**
     * Calculates the average mission quota.
     * Precondition: locations may be null or contain null entries.
     * Postcondition: returns the average quota, or 0.0 if no locations exist.
     */
    public static double calculateAverageQuota(
            DeploymentLocation[] locations) {
        if (locations == null) {
            return 0.0;
        }

        int total = 0;
        int count = 0;

        for (DeploymentLocation location : locations) {
            if (location != null) {
                total += location.getWinQuota();
                count++;
            }
        }

        if (count == 0) {
            return 0.0;
        }

        return (double) total / count;
    }

    }

