package vehicles;

import routes.Route;

/**
 * Represents a train in the transportation network.
 */
public class Train extends PublicTransport {
    private int carriageCount;
    //    IncompatibleTypeException thrown when a subclass of PublicTransport is added to a Route for a different type of transport. i.e. Train added to BusRoute

    /**
     * Creates a new Train object with the given id, capacity, route, and carriage count.
     * <p>
     * Should meet the specification of {@link PublicTransport#PublicTransport(int, int, Route)},
     * as well as extending it to include the following:
     *
     * If the given carriage count is less than or equal to zero, then 1 should be stored instead.
     * </p>
     * @param id The identifying number of the train.
     * @param capacity The maximum capacity of the train.
     * @param route The route this train is on.
     * @param carriageCount The number of carriages this train has.
     */
    public Train(int id, int capacity, Route route, int carriageCount) {
        super(id, capacity, route);
        this.carriageCount = carriageCount <= 0 ? 1 : carriageCount;
    }

    /**
     * Returns the number of carriages this train has.
     * @return The number of carriages the train has.
     */
    public int getCarriageCount() {
        return carriageCount;
    }
}
