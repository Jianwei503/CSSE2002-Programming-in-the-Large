package routes;

import exceptions.EmptyRouteException;
import exceptions.IncompatibleTypeException;
import stops.Stop;
import vehicles.PublicTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a route in the transportation network.
 *
 * A route is essentially a collection of stops which
 * public transport vehicles can follow.
 */
public abstract class Route {
    protected String name;
    protected int routeNumber;
    protected List<Stop> stopsOnRoute = new ArrayList<>();
    protected Stop startStop;
    protected List<PublicTransport> transports = new ArrayList<>();

    /**
     * Creates a new Route with the given name and number.
     * <p>
     * The route should initially have no stops or vehicles on it.
     *
     * If the given name contains any newline characters ('\n') or carriage returns
     * ('\r'), they should be removed from the string before it is stored.
     *
     * If the given name is null, an empty string should be stored in its place.
     * </p>
     * @param name The name of the route.
     * @param routeNumber The route number of the route.
     */
    public Route(String name, int routeNumber) {
        if ( Objects.isNull(name) ) {
            this.name = "";
        } else {
            this.name = name.replaceAll("\\r\\n|\\r|\\n","");
        }
        this.routeNumber = routeNumber;
    }

    /**
     * Returns the name of the route.
     * @return The route name.
     */
    public String getName() {return name;}


    /**
     * Returns the number of the route.
     * @return The route number.
     */
    public int getRouteNumber(){
        return this.routeNumber;
    }
    //EmptyRouteException thrown when a PublicTransport is added to a Route with no Stops.

    /**
     * Returns the stops which comprise this route.
     * <p>
     * The order of the stops in the returned list should be the same as
     * the order in which the stops were added to the route.
     *
     * Modifying the returned list should not result in changes to the
     * internal state of the class.
     * </p>
     * @return The stops making up the route.
     */
    public List<Stop> getStopsOnRoute() {

        List<Stop> deepCopiedList = new ArrayList<>();

        for(Stop s  : this.stopsOnRoute) {
            deepCopiedList.add(s);
        }
        return deepCopiedList;
    }

    /**
     * Returns the first stop of the route (i.e. the first stop to be added to the route).
     * @return The start stop of the route.
     * @throws EmptyRouteException if there are no stops currently on the route
     */
    public Stop getStartStop() throws EmptyRouteException {
        if ( this.stopsOnRoute.isEmpty() ) {
            throw new EmptyRouteException();
        } else {
//            return this.stopsOnRoute.get(0);
            return this.startStop;
        }
    }

    /**
     * Adds a stop to the route.
     * <p>
     * If the given stop is null, it should not be added to the route.
     *
     * If this is the first stop to be added to the route, the given stop should be
     * recorded as the starting stop of the route. Otherwise, the given stop should
     * be recorded as a neighbouring stop of the previous stop on the route
     * (and vice versa) using the {@link Stop#addNeighbouringStop(Stop)} method.
     *
     * This route should also be added as a route of the given stop (if the given stop
     * is not null) using the {@link Stop#addRoute(Route)} method.
     * </p>
     * @param stop The stop to be added to this route.
     */
    public void addStop(Stop stop) {
        if ( Objects.nonNull(stop) ) {
            if ( this.stopsOnRoute.isEmpty() ) {
                this.stopsOnRoute.add( stop );
                this.startStop = stop;
            } else {
                //the index of previous stop in stopsOnRoute list.
                int index = this.stopsOnRoute.size() - 1;
                Stop previousStop = this.stopsOnRoute.get(index);
                previousStop.addNeighbouringStop(stop);
                stop.addNeighbouringStop(previousStop);
                this.stopsOnRoute.add( stop );
            }
            stop.addRoute( this );
        }
    }

    /**
     * Returns the public transport vehicles currently on this route.
     * <p>
     * No specific order is required for the public transport objects in the returned list.
     *
     * Modifying the returned list should not result in changes to the internal state of the class.
     * </p>
     * @return The vehicles currently on the route.
     */
    public List<PublicTransport> getTransports() {

        List<PublicTransport> deepCopiedList = new ArrayList<>();

        for(PublicTransport p  : this.transports) {
            deepCopiedList.add(p);
        }
        return deepCopiedList;
    }

    /**
     * Adds a vehicle to this route.
     * <p>
     * If the given transport is null, it should not be added to the route.
     *
     * The method should check for the transport being null first, then for an empty
     * route, and then for incompatible types (in that order).
     * </p>
     * @param transport The vehicle to be added to the route.
     * @throws EmptyRouteException if there are not yet any stops on the route.
     * @throws IncompatibleTypeException If the given vehicle is of the incorrect type
     *                                   for this route. This depends on the type of the
     *                                   route, i.e. a BusRoute can only accept Bus instances.
     */
    public void addTransport(PublicTransport transport)
            throws EmptyRouteException,
            IncompatibleTypeException {

        if ( Objects.nonNull(transport) ) {
            if ( this.stopsOnRoute.isEmpty() ){
                throw new EmptyRouteException();
            } else if ( !( this.getType().equals( transport.getType() ) ) ) {
                throw new IncompatibleTypeException();
            }
            this.transports.add( transport );
        }
    }

    /**
     * Returns the type of this route.
     * @return The type of the route (see subclasses)
     */
    public abstract String getType();

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.routeNumber);
    }

    /**
     * Compares this stop with another object for equality. Two routes are equal
     * if their names and route numbers are equal.
     * @param other The other object to compare for equality.
     * @return True if the objects are equal (as defined above), false otherwise
     * (including if other is null or not an instance of the Route class.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (Objects.isNull(other)) {
            return false;
        }
        if (!(other instanceof Route)) {
            return false;
        }
        Route otherObj = (Route) other;

        if ( this.name.equals( otherObj.getName() ) &&
                this.routeNumber == otherObj.getRouteNumber() ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a string representation of a route in the format:
     * '{type},{name},{number}:{stop0}|{stop1}|...|{stopN}'
     * <p>
     * without the surrounding quotes, and where {type} is replaced by the type of
     * the route, {name} is replaced by the name of the route, {number} is replaced
     * by the route number, and {stop0}|{stop1}|...|{stopN} is replaced by a list of
     * the names of the stops stops making up the route. For example:
     *
     * bus,red,1:UQ Lakes|City|Valley
     * </p>
     * @return A string representation of the route.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for ( Stop stop : this.stopsOnRoute ) {
            stringBuilder.append( String.format( "%s|",stop.getName() ) );
        }
        String substring = stringBuilder.substring( 0,stringBuilder.length()-1 );
        return getType() + "," +
                name + "," +
                routeNumber + ":" +
                substring;
    }
}
