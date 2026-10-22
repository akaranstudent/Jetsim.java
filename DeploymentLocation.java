/**
 * Stores information about a deployment location and its mission settings.
 */
public class DeploymentLocation {
private String name;
private int aircraftChance;
private int groundChance;
private int unknownAircraftChance;
private int winQuota;

/**
 * Counts how many DeploymentLocation objects have been created.
 * This variable is shared by all objects of this class.
 */
private static int locationsCreated = 0;

    /**
     * Creates a deployment location with its event chances and mission quota.
     * @param name the location name
     * @param aircraftChance the aircraft event threshold
     * @param groundChance the ground event threshold
     * @param unknownAircraftChance the unknown aircraft event threshold
     * @param winQuota the number of enemies needed to complete a mission
     */
    public DeploymentLocation(String name, int aircraftChance, int groundChance, int unknownAircraftChance, int winQuota) {
        this.name = name;
        this.aircraftChance = aircraftChance;
        this.groundChance = groundChance;
        this.unknownAircraftChance = unknownAircraftChance;
        this.winQuota = winQuota;
        locationsCreated++;
    }

    /**
     * Creates a location with default event chances.
     * @param name the location name
     * @param winQuota the number of enemies needed to complete a mission
     */
    public DeploymentLocation(String name, int winQuota) {
    this(name, 15, 35, 60, winQuota);
}

    /** @return the name of the location */
    public String getName() {
        return name;
    }

    /** @return the aircraft event threshold */
    public int getAircraftChance() {
        return aircraftChance;
    }

    /** @return the ground event threshold */
    public int getGroundChance() {
        return groundChance;
    }

    /** @return the unknown aircraft event threshold */
    public int getUnknownAircraftChance() {
        return unknownAircraftChance;
    }

    /** @return the mission quota */
    public int getWinQuota() {
        return winQuota;
    }

    /**
     * Returns the location's information as a String.
     * @return the location information
     */
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
     * @param locations the array of locations to check
     * @param minimumQuota the minimum quota to count
     * @return the number of locations meeting the minimum quota
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
     * @param locations the array of locations to search
     * @param targetName the name to look for
     * @return the matching location, or null if it is not found
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
     * @param locations the array of locations to check
     * @return the average quota, or 0.0 if there are no locations
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

    /**
     * Changes the mission quota for this location.
     * @param winQuota the new mission quota
     */
    public void setWinQuota(int winQuota) {
        this.winQuota = winQuota;
    }

    /**
     * Gets the number of deployment locations created.
     * This method is static because the count belongs to the class, not one object.
     * @return the total number of locations created
     */
    public static int getLocationsCreated() {
        return locationsCreated;
    }
}