package routes;

import exceptions.EmptyRouteException;
import exceptions.IncompatibleTypeException;
import org.junit.Before;
import org.junit.Test;
import stops.Stop;
import vehicles.Bus;
import vehicles.Ferry;
import vehicles.PublicTransport;

import java.util.List;

import static org.junit.Assert.*;

public class RouteTest {
    Route busRoute = new BusRoute( "AcaciaRidge\n-\rWoolloongabba",115);

    @Before
    public void setUp() throws Exception {
        busRoute.addStop( new Stop("Acacia Ridge",-30,160 ) );
    }

    @Test
    public void getName() {
        assertEquals("AcaciaRidge-Woolloongabba",busRoute.getName());
    }

    @Test
    public void getNameNullValue() {
        Route route = new BusRoute( null,115);
        assertEquals("",route.getName());

    }

    @Test
    public void getRouteNumber() {
        assertEquals(115,busRoute.getRouteNumber());
    }

    @Test
    public void getStopsOnRoute() {
        Stop neighbourStop = new Stop("Archerfield",-27,154 );
        busRoute.addStop(neighbourStop);

        List<Stop> stopsOnRoute = busRoute.getStopsOnRoute();
        assertEquals("Acacia Ridge",stopsOnRoute.get(0).getName());
        assertEquals("Archerfield",stopsOnRoute.get(1).getName());

        stopsOnRoute.clear();
        assertEquals(0,stopsOnRoute.size());
        assertEquals(2,busRoute.getStopsOnRoute().size());
    }

    @Test
    public void getStartStop() throws EmptyRouteException {
        assertEquals("Acacia Ridge",busRoute.getStartStop().getName());
    }

    @Test (expected = EmptyRouteException.class)
    public void getStartStopThrowsException() throws EmptyRouteException {
        Route route = new BusRoute( "AcaciaRidge\n-\rWoolloongabba",115);
        route.getStartStop();
    }

    @Test
    public void addStop() throws EmptyRouteException {
        assertEquals("Acacia Ridge",busRoute.getStartStop().getName());

        Stop neighbourStop1 = new Stop("Archerfield",-27,154 );
        Stop neighbourStop2 = new Stop("Inala",-26,156 );
        busRoute.addStop(neighbourStop1);
        busRoute.addStop(neighbourStop2);

        assertEquals(3,busRoute.getStopsOnRoute().size());

        assertEquals("Acacia Ridge", neighbourStop1.getNeighbours().get(0).getName() );
        assertEquals("Inala", neighbourStop1.getNeighbours().get(1).getName() );
        assertEquals("Archerfield", neighbourStop2.getNeighbours().get(0).getName() );

        assertEquals("AcaciaRidge-Woolloongabba", neighbourStop1.getRoutes().get(0).getName() );
        assertEquals("AcaciaRidge-Woolloongabba", neighbourStop2.getRoutes().get(0).getName() );
    }

    @Test
    public void addStopNullValue() {
        busRoute.addStop(null);
        assertEquals(1,busRoute.getStopsOnRoute().size());
    }

    @Test
    public void getTransports() throws IncompatibleTypeException, EmptyRouteException {
        assertEquals(0,busRoute.getTransports().size());

        PublicTransport bus = new Bus(1,50,busRoute,"078TZL");
        busRoute.addTransport(bus);

        List<PublicTransport> transports = busRoute.getTransports();
        assertEquals(1,transports.get(0).getId());

        transports.clear();
        assertEquals(0,transports.size());
        assertEquals(1,busRoute.getTransports().size());
    }

    @Test
    public void addTransport() throws IncompatibleTypeException, EmptyRouteException {
        assertEquals(0,busRoute.getTransports().size());

        PublicTransport bus = new Bus(1,50,busRoute,"078TZL");
        busRoute.addTransport(bus);

        List<PublicTransport> transports = busRoute.getTransports();
        assertEquals(1,transports.get(0).getId());
    }

    @Test
    public void addTransportNullValue() throws IncompatibleTypeException, EmptyRouteException {
        assertEquals(0,busRoute.getTransports().size());
        busRoute.addTransport(null);
        assertEquals(0,busRoute.getTransports().size());
    }

    @Test ( expected = EmptyRouteException.class )
    public void addTransportEmptyRoute() throws IncompatibleTypeException, EmptyRouteException {
        Route route = new BusRoute( "AcaciaRidge\n-\rWoolloongabba",115);
        PublicTransport bus = new Bus(1,50,route,"078TZL");
        route.addTransport(bus);
    }

    @Test (expected = IncompatibleTypeException.class)
    public void addTransportIncompatibleType() throws IncompatibleTypeException, EmptyRouteException {
        Route ferryRoute = new FerryRoute("UQ-City",326);
        PublicTransport ferry = new Ferry(5,20,ferryRoute,null);

        busRoute.addTransport(ferry);
    }

    @Test
    public void hashCode1() {
        Route busRoute = new BusRoute( "AcaciaRidge-Woolloongabba",110);
        Route busRoute1 = new BusRoute( "AcaciaRidge-Woolloongabba",110);

        assertTrue(busRoute.hashCode() == busRoute1.hashCode() );
    }

    @Test
    public void equals1() {
        Route busRoute = new BusRoute( "AcaciaRidge-Woolloongabba",110);
        Route busRoute1 = new BusRoute( "AcaciaRidge-Woolloongabba",110);
        assertTrue(busRoute.equals(busRoute));
        assertTrue(busRoute.equals(busRoute1));
    }

    @Test
    public void notEquals() {
        Route busRoute = new BusRoute( "AcaciaRidge-Woolloongabba",110);
        Route busRoute1 = new BusRoute( "AcaciaRidge-City",117);

        assertFalse(busRoute.equals(busRoute1));
    }

    @Test
    public void toString1() {
        Stop neighbourStop1 = new Stop("Archerfield",-27,154 );
        Stop neighbourStop2 = new Stop("Inala",-26,156 );
        busRoute.addStop(neighbourStop1);
        busRoute.addStop(neighbourStop2);

        assertEquals("bus,AcaciaRidge-Woolloongabba,115:Acacia Ridge|Archerfield|Inala",
                busRoute.toString());
    }

    @Test
    public void getType() {
        assertEquals("bus",busRoute.getType());
    }
}