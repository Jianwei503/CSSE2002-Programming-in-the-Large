package vehicles;

import routes.Route;

import java.util.Objects;

/**
 * Represents a bus in the transportation network.
 */
public class Bus extends PublicTransport{
    private String registrationNumber;

    /**
     * Creates a new Bus object with the given id, capacity, route, and registration number.
     * <p>
     * Should meet the specification of {@link PublicTransport#PublicTransport(int, int, Route)},
     * as well as extending it to include the following:
     *
     * If the given registration number is null, an empty string should be stored instead.
     * If the registration number contains any newline characters ('\n') or carriage
     * returns ('\r'), they should be removed from the string before it is stored.
     * </p>
     * @param id The identifying number of the bus.
     * @param capacity The maximum capacity of the bus.
     * @param route The route this bus is on.
     * @param registrationNumber The registration number of the bus.
     */
    public Bus(int id, int capacity, Route route, String registrationNumber) {
        super(id, capacity, route);
        if ( Objects.isNull( registrationNumber ) ) {
            this.registrationNumber = "";
        } else {
            this.registrationNumber = registrationNumber.replaceAll(
                    "\\r\\n|\\r|\\n","");
        }
    }

    /**
     * Returns the registration number of this bus.
     * @return The registration number of the bus.
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }
}
