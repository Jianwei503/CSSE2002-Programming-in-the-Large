package vehicles;

import routes.Route;

import java.util.Objects;

/**
 * Represents a ferry in the transportation network.
 */
public class Ferry extends PublicTransport {
    private String ferryType;

    /**
     * Creates a new Ferry object with the given id, capacity, route, and type.
     * <p>
     * Should meet the specification of {@link PublicTransport#PublicTransport(int, int, Route)},
     * as well as extending it to include the following:
     *
     * If the given ferryType is null or empty, the string "CityCat" should be stored
     * instead. If the ferry type contains any newline characters ('\n') or carriage
     * returns ('\r'), they should be removed from the string before it is stored.
     * </p>
     * @param id The identifying number of the ferry.
     * @param capacity The maximum capacity of the ferry.
     * @param route The route this ferry is on.
     * @param ferryType The type of the ferry (e.g. CityCat).
     */
    public Ferry(int id, int capacity, Route route, String ferryType) {
        super(id, capacity, route);
        if (Objects.isNull(ferryType) || ferryType.isEmpty()) {
            this.ferryType = "CityCat";
        } else {
            this.ferryType = ferryType.replaceAll(
                    "\\r\\n|\\r|\\n","");
        }
    }

    /**
     * Returns the type of this ferry.
     * @return The type of the ferry.
     */
    public String getFerryType() {
        return ferryType;
    }
}
