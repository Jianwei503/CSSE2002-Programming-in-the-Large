package stops;

import exceptions.NoNameException;
import passengers.Passenger;
import routes.Route;
import vehicles.PublicTransport;

import java.util.*;

/**
 * Represents a stop in the transportation network.
 * <p>Stops are where public transport vehicles collect and drop off passengers,
 * and are located along one or more routes.</p>
 */
public class Stop {
    private String name;
    private int x;
    private int y;
    private List<Route> routes = new ArrayList<>();
    private List<Stop> neighbours = new ArrayList<>();
    private List<Passenger> waitingPassengers = new ArrayList<>();
    private List<PublicTransport> vehicles = new ArrayList<>();

    /**
     * Creates a new Stop object with the given name and coordinates.
     * <p>A stop should be created with no passengers, routes, or vehicles.
     * If the given name contains any newline characters ('\n') or carriage returns ('\r'),
     * they should be removed from the string before it is stored.</p>
     * @param name The name of the stop being created.
     * @param x The x coordinate of the stop being created.
     * @param y The y coordinate of the stop being created.
     * @throws if the given name is null or empty.
     */
    public Stop(String name, int x, int y) throws NoNameException {
        if ( Objects.isNull(name) || name.isEmpty() ) {
            throw new NoNameException();
        } else {
            this.name = name.replaceAll("\\r\\n|\\r|\\n","");
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Returns the name of this stop.
     * @return The name of the stop.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the x-coordinate of this stop.
     * @return The x-coordinate of the stop.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this stop.
     * @return The y-coordinate of the stop.
     */
    public int getY() {
        return y;
    }

    /**
     * Records that this stop is part of the given route.
     * If the given route is null, it should not be added to the stop.
     * @param route The route to be added.
     */
    public void addRoute(Route route) {
        if( Objects.nonNull(route) ){
            this.routes.add(route);
        }
    }

    /**
     * Returns the routes associated with this stop.
     * <p>
     * No specific order is required for the route objects in the returned list.
     *
     * Modifying the returned list should not result in changes to the internal
     * state of the class.</p>
     * @return The routes which go past the stop.
     */
    public List<Route> getRoutes() {

        List<Route> deepCopiedList = new ArrayList<>();

        for(Route r  : this.routes) {
            deepCopiedList.add(r);
        }
        return deepCopiedList;
    }

    /**
     * Records the given stop as being a neighbour of this stop.
     * <p>
     *  If the given stop is null, or if this stop is already recorded as a neighbour,
     * it should not be added as a neighbour, and the method should return early.
     * </p>
     * @param neighbour The stop to add as a neighbour.
     */
    public void addNeighbouringStop(Stop neighbour) {
        if(Objects.nonNull(neighbour) && !this.neighbours.contains(neighbour)) {
            this.neighbours.add(neighbour);
        }
    }

    /**
     * Returns all of the stops adjacent to this one on any routes.
     * <p>
     * No specific order is required for the stop objects in the returned list.
     *
     * Modifying the returned list should not result in changes to the internal
     * state of the class.</p>
     * @return The neighbours of this stop.
     */
    public List<Stop> getNeighbours() {
        List<Stop> deepCopiedList = new ArrayList<>();

        for(Stop s  : this.neighbours) {
            deepCopiedList.add(s);
        }
        return deepCopiedList;
    }

    /**
     * Places a passenger at this stop.
     * If the given passenger is null, it should not be added to the stop.
     * @param passenger The passenger to add to the stop.
     */
    public void addPassenger(Passenger passenger) {
        if (Objects.nonNull(passenger)) {
            this.waitingPassengers.add(passenger);
        }
    }

    /**
     * Returns the passengers currently at this stop.
     * <p>
     * The order of the passengers in the returned list should be the same as
     * the order in which the passengers were added to the stop.
     *
     * Modifying the returned list should not result in changes to the internal
     * state of the class.</p>
     * @return The passengers currently waiting at the stop.
     */
    public List<Passenger> getWaitingPassengers() {

        List<Passenger> deepCopiedList = new ArrayList<>();

        for(Passenger p  : this.waitingPassengers) {
            deepCopiedList.add(p);
        }
        return deepCopiedList;
    }

    /**
     * Checks whether the given public transport vehicle is at this stop or not.
     * @param transport The transport vehicle to check for.
     * @return True if the vehicle is at this stop, false otherwise.
     */
    public boolean isAtStop(PublicTransport transport) {
        return this.vehicles.contains(transport);
    }

    /**
     * Returns the vehicles currently at this stop.
     * <p>
     * No specific order is required for the public transport objects in the returned list.
     * Modifying the returned list should not result in changes to the internal state of
     * the class.</p>
     * @return The vehicles currently at the stop.
     */
    public List<PublicTransport> getVehicles() {

        List<PublicTransport> deepCopiedList = new ArrayList<>();

        for(PublicTransport p  : this.vehicles) {
            deepCopiedList.add(p);
        }
        return deepCopiedList;
    }

    /**
     * Records a public transport vehicle arriving at this stop. There is no limit on
     * the number of vehicles that can be at a stop simultaneously.
     *
     * <p>If the given vehicle is already at this stop, or if the vehicle is null, do nothing.
     * Otherwise, unload all of the passengers on the arriving vehicle(using
     * {@link PublicTransport#unload()}),and place them at this stop, as well as recording the
     * vehicle itself at this stop.
     *
     * This method does not need to check whether this stop is on the given transport's route,
     * or whether the transport's route is a route of this stop, and should also not update
     * the location of the transport.</p>
     * @param transport The public transport vehicle arriving at this stop.
     */
    public void transportArrive(PublicTransport transport) {
        if ( Objects.nonNull(transport) && !this.vehicles.contains(transport) ) {
            this.waitingPassengers.addAll(transport.unload());
            this.vehicles.add(transport);
        }
    }

    /**
     * Records a public transport vehicle departing this stop and travelling to a new stop.
     *<p>This method should also update the vehicle's location to be the next stop (using
     * {@link PublicTransport#travelTo(Stop)}).
     *
     * If the given vehicle is not at this stop, or if the vehicle is null, or if the next
     * stop is null, do nothing.</p>
     * @param transport The transport currently leaving this stop.
     * @param nextStop The new stop that the transport is travelling to.
     */
    public void transportDepart(PublicTransport transport, Stop nextStop) {
        if ( Objects.nonNull(transport) && Objects.nonNull(nextStop)
                && this.vehicles.contains(transport) ) {
            this.vehicles.remove(transport);
            nextStop.vehicles.add(transport);
            transport.travelTo(nextStop);
        }
    }

    /**
     * Returns the Manhattan distance between this stop and the given other stop.
     * <p>
     * Manhattan distance between two points, for example (x1, y1) and (x2, y2),
     * is calculated using the following formula:
     * abs(x1 - x2) + abs(y1 - y2)
     * where abs is a method that calculates the absolute value of a number.</p>
     * @param stop The stop to calculate the Manhattan distance to.
     * @return The Manhattan distance between this stop and the given stop
     *          (or -1 if the given stop is null)
     */
    public int distanceTo(Stop stop) {
        if ( Objects.isNull(stop) ) {
            return -1;
        } else {
            return Math.abs( this.x - stop.x ) + Math.abs( this.y - stop.y );
        }
    }

    @Override
    public int hashCode() {
        Set hashSet = new HashSet();
        for (Route r : routes) {
            hashSet.add(r.getRouteNumber());
            //form a hashSet with routes nums of this stop
        }
        return Objects.hash(name, x, y, hashSet);
    }

    /**
     * Compares this stop to the other object for equality.
     * <p>
     * Two stops are considered equal if they have the same name, x-coordinate,
     * y-coordinate, and routes. Routes may be in any order, as long as all of
     * this stop's routes are also associated with the other stop, and vice versa.
     * Duplicates of routes do not need to be considered in determining equality
     * (that is, if this stop has routes R1 and R2, and other has routes R1, R2, and
     * R1 again, their routes can still be considered equal, ignoring duplicates).
     * </p>
     * @param other the other object to compare for equality.
     * @return True if the objects are equal (as defined above), false otherwise
     *          (including if other is null or not an instance of the Stop class.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other ) {
            return true;
        }
        if (Objects.isNull(other)) {
            return false;
        }
        if (!(other instanceof Stop)) {
            return false;
        }
        Stop otherObj = (Stop) other;
        if (this.hashCode() == otherObj.hashCode()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a string representation of a stop in the format:
     * '{name}:{x}:{y}'
     * <p>without the surrounding quotes, and where {name} is replaced
     * by the name of the stop, {x} is replaced by the x-coordinate of
     * the stop, and {y} is replaced by the y-coordinate of the stop.</p>
     * @return A string representation of the stop.
     */
    @Override
    public String toString() {
        return "{" + this.getName() + "} : {"
                + this.getX() + "} : {"
                + this.getY() + "}";
    }
}
